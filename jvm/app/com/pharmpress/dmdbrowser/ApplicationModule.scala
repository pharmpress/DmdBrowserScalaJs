package com.pharmpress.dmdbrowser

import com.google.inject.AbstractModule
import com.pharmpress.elasticsearch.{Elastic4sServiceImpl, IElasticSearch}
import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri}
import org.elasticsearch.common.settings.{ImmutableSettings, Settings}
import play.api.{Configuration, Environment, Logger}

/**
  * Created by jimbo on 15/09/16.
  */
class ApplicationModule(
                         environment: Environment,
                         configuration: Configuration
                       ) extends AbstractModule {

  def configure: Unit = {

    val conf = configuration.underlying
    val rpsConfigUri = conf.getString("es.rps.client.uri")
    val rpsConfigCluster = conf.getString("es.rps.cluster.name")

    bind(classOf[IElasticSearch]) toInstance new Elastic4sServiceImpl(() => {
      Logger.info(s"Connecting to Elasticsearch at $rpsConfigUri with cluster $rpsConfigCluster")
      ElasticClient.remote(
        ImmutableSettings.settingsBuilder()
          .classLoader(classOf[Settings].getClassLoader)
          .put("cluster.name", rpsConfigCluster)
          .build(),
        ElasticsearchClientUri(rpsConfigUri)
      )
    })
  }
}
