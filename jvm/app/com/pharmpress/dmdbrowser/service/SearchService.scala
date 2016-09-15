package com.pharmpress.dmdbrowser.service

import com.pharmpress.elasticsearch.IElasticSearch
import com.sksamuel.elastic4s.ElasticDsl._
/**
  * @author kostas.kougios
  *         Date: 15/09/16
  */
class SearchService(elastic:IElasticSearch)
{
  def searchAll(q:String) = {
    elastic.exec(
      search in("amp","vmp","vtm","ampp") query q
    ).map {
      r=>
    }

  }
}
