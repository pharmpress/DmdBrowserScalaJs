package com.pharmpress.elasticsearch

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

import com.sksamuel.elastic4s._
import org.elasticsearch.action.count.CountResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.transport.NoNodeAvailableException
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Try

class Elastic4sServiceImpl(
  clientFactory: () => ElasticClient,
  maxRetries: Int = 1,
  coolOfBeforeRetryMS: Int => Long = retryNo => retryNo * 100
) extends IElasticSearch {

  import com.sksamuel.elastic4s.ElasticDsl._

  private val Log = LoggerHelper.Logger


  /**
    * Increase default timeout of 10 seconds as bulk load takes longer.
    */
  implicit private val timeout = 2 minutes

  private var internalClient: ElasticClient = clientFactory()

  private val connectionAttempts = new AtomicInteger(0)

  private var lastAccessed = System.currentTimeMillis()

  def shutdown(): Unit = {
    internalClient.close()
  }

  override def getClient: ElasticClient = {

    // Check connection every minute (not thread-safe but does it matter?)
    if (FiniteDuration(System.currentTimeMillis - lastAccessed, TimeUnit.MILLISECONDS) > (1 minute)) {
      synchronized {
        Try {
          Await.result(internalClient.exists("anyoldindex"), 5 seconds)
          // Connection successful
          //          connectionAttempts.set(0)
        } recover {
          case nne: NoNodeAvailableException =>
            Log.warn("NoNodeAvailableException. Recreating ES connection")
            connectionAttempts.incrementAndGet()
            internalClient.close()
            internalClient = clientFactory()
          case te: TimeoutException => {
            Log.warn("TimeoutException. Recreating ES connection")
            connectionAttempts.incrementAndGet()
            internalClient.close()
            internalClient = clientFactory()
          }
        }
      }
    }

    lastAccessed = System.currentTimeMillis()
    internalClient
  }


  override def countDocsAsync(indexName: String, docType: String): Future[CountResponse] = {
    getClient.execute {
      count from indexName -> docType
    }
  }

  override def countDocs(indexName: String, docType: String): CountResponse = countDocsAsync(indexName, docType).await



  override def getDocByIdAsync[T](indexName: String, docType: String, id: String)(implicit reads: Reads[T]): Future[Option[T]] = {
    getClient.execute {
      get id id from indexName -> docType
    }.map { response =>
      if (!response.isExists || response.getSourceAsString == null) {
        None
      } else {
        Json.fromJson(Json.parse(response.getSourceAsString))(reads).asOpt match {
          case Some(result) => Some(result)
          case _            => throw new IllegalStateException(s"Error deserialising $indexName/$docType $id")
        }
      }
    }
  }

  private def logQuery[T](q: T): Unit = {
    if (Log.isDebugEnabled) {
      q match {
        case s: SearchDefinition =>
          Log.debug(s"ES query : ${s.show}")
        case s: GetDefinition =>
          Log.debug(s"ES get : $s")
        case s: MultiSearchDefinition =>
          Log.debug(s"ES query : ${s.show}")
        case s: CountDefinition =>
          Log.debug(s"ES count query")
        case other =>
          Log.debug(s"ES other unmatched query: $other")
      }
    }
  }

  override def exec[T, R, Q](t: T)(implicit executable: Executable[T, R, Q]): Future[Q] =  {
    logQuery(t)
    val f = getClient.execute(t)
    if (Log.isDebugEnabled) {
      f.onSuccess {
        case response =>
          Log.debug(s"response : $response")
      }
    }
    f
  }

  type IndexName = String
  type IndexType = String
  type DocId = String


  override def searchDocsAsync(indexName: IndexName, indexType: IndexType, queryString: String, numResults: Int,
    startResult: Int = 0, idPrefixOption: Option[String] = None): Future[(Long, Seq[RichSearchHit])] = {
    getClient
      .execute {
        val searchDefinition = (search in indexName -> indexType)
          .start(startResult)
          .limit(numResults)
          .fetchSource(false)
          .query(queryString)

        idPrefixOption
          .map(idPrefix => searchDefinition.postFilter(prefixFilter("_id", idPrefix)))
          .getOrElse(searchDefinition)
      }
      .map { response =>
        response.getHits.totalHits -> response.hits.toSeq
      }
  }

  override def searchForDocAsync[T](indexName: String, indexType: String, queryDef: QueryDefinition)(implicit reads: Reads[T]): Future[T] = {
    getClient
      .execute {
        search in indexName -> indexType limit 1 query queryDef
      }.map {
      _.doGetOne(reads)
    }
  }

  override def searchForDocsAsync[T](indexName: String, indexType: String, queryDef: QueryDefinition)(implicit reads: Reads[T]): Future[Seq[T]] = {
    getClient
      .execute {
        search in indexName -> indexType limit 1 query queryDef
      }.map {
      _.doGet(reads)
    }
  }

  implicit class SearchResponseJson(response: SearchResponse) {
    def doGet[T](reads: Reads[T]): Seq[T] = {
      try {
        response.getHits.hits().map { searchHit =>
          Json.fromJson(Json.parse(searchHit.getSourceAsString))(reads) match {
            case s: JsSuccess[T] => s.get
            case e: JsError      => throw new IllegalStateException(s"Error deserialising ${searchHit.getSourceAsString}.\nErrors:\n" + JsError.toJson(e).toString())
          }
        }
      } catch {
        case e: Throwable =>
          Log.error(s" error in :$e $response")
          throw e
      }
    }

    def doGetOne[T](reads: Reads[T]): T = {
      try {
        response.getHits.hits().headOption.map { searchHit =>
          Json.fromJson(Json.parse(searchHit.getSourceAsString))(reads) match {
            case s: JsSuccess[T] => s.get
            case e: JsError      => throw new IllegalStateException(s"Error deserialising ${searchHit.getSourceAsString}\n.Errors:\n" + JsError.toJson(e).toString())
          }
        } match {
          case Some(result) => result
          case _            => throw new IllegalArgumentException(s"Could not find matching document")
        }
      } catch {
        case e: Throwable =>
          Log.error(s" error in :$e $response")
          throw e
      }
    }
  }

}

