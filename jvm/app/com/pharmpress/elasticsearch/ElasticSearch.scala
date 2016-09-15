package com.pharmpress.elasticsearch

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.sksamuel.elastic4s._
import com.sksamuel.elastic4s.mappings.MappingDefinition
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.admin.indices.flush.FlushResponse
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse
import org.elasticsearch.action.admin.indices.status.IndicesStatusResponse
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.count.CountResponse
import org.elasticsearch.action.delete.DeleteResponse
import org.elasticsearch.action.get.{GetResponse, MultiGetResponse}
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.cluster.metadata.IndexMetaData
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.search.sort.SortOrder
import org.elasticsearch.search.{SearchHit, SearchHits}
import play.api.libs.json.{JsValue, Reads}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

@deprecated("use guice instead", "20/05/2015")
trait ElasticSearchClient {

}
@deprecated("use guice instead", "20/05/2015")
trait ElasticSearchClientConfig {

}

@deprecated("use guice instead", "20/05/2015")
trait ElasticSearch {
  type IElasticSearch = com.pharmpress.elasticsearch.IElasticSearch
  val elasticSearch: IElasticSearch
}

// scalastyle:off number.of.methods
trait IElasticSearch {

  def shutdown(): Unit

  def getClient: ElasticClient

  def countDocs(indexName: String, docType: String): CountResponse

  def countDocsAsync(indexName: String, docType: String): Future[CountResponse]

  def getDocByIdAsync[T](indexName: String, docType: String, id: String)(implicit reads: Reads[T]): Future[Option[T]]

  def searchDocsAsync(
    indexName: String,
    indexType: String,
    queryString: String,
    numResults: Int,
    startResult: Int = 0,
    idPrefixOption: Option[String] = None
  ): Future[(Long, Seq[RichSearchHit])]

  def searchForDocAsync[T](indexName: String, indexType: String, queryDef: QueryDefinition)(implicit reads: Reads[T]): Future[T]

  /**
   * execute any ES Elastic4s DSL command
   *
   * @param t  this should be the Elastic4s DSL command
   * @param executable (provided by es4s)
   * @return Future with the results of the query
   */
  def exec[T, R, Q](t: T)(implicit executable: Executable[T, R, Q]): Future[Q]
}

object IElasticSearch {

  def remote(clusterName: String, uri: String): IElasticSearch = {
    val settings = ImmutableSettings.settingsBuilder()
      .put("cluster.name", clusterName)
      .build()

    new Elastic4sServiceImpl(() => {
      ElasticClient.remote(
        settings,
        ElasticsearchClientUri(uri)
      )
    })
  }

}