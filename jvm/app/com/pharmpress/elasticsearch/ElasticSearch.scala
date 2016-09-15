package com.pharmpress.elasticsearch

import com.sksamuel.elastic4s._
import org.elasticsearch.action.count.CountResponse
import org.elasticsearch.common.settings.ImmutableSettings
import play.api.libs.json.Reads

import scala.concurrent.Future

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

  def searchForDocsAsync[T](indexName: String, indexType: String, queryDef: QueryDefinition)(implicit reads: Reads[T]): Future[Seq[T]]

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