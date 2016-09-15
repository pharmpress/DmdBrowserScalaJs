package com.pharmpress.dmdbrowser.service

import javax.inject.Inject

import com.pharmpress.common.model.dmd.{Amp, Ampp, Vmp, Vtm}
import com.pharmpress.elasticsearch.IElasticSearch
import com.sksamuel.elastic4s.ElasticDsl._
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits._

/**
  * @author kostas.kougios
  *         Date: 15/09/16
  */
class SearchService @Inject()(elastic: IElasticSearch)
{
  def searchAll(q: String) = {
    elastic.exec(
      search in("amp", "vmp", "vtm", "ampp") query q
    ).map {
      r =>
        r.hits.map {
          hit =>
            hit.`type` match {
              case "amp" => Json.fromJson[Amp](Json.parse(hit.source)).get
              case "vmp" => Json.fromJson[Vmp](Json.parse(hit.source)).get
              case "vtm" => Json.fromJson[Vtm](Json.parse(hit.source)).get
              case "ampp" => Json.fromJson[Ampp](Json.parse(hit.source)).get
            }
        }
    }
  }
}
