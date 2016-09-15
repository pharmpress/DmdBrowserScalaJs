package com.pharmpress.dmdbrowser.service

import javax.inject.Inject

import com.pharmpress.common.model.dmd.{Amp, Ampp, DmdIdentifiable, Vmp, Vmpp, Vtm}
import com.pharmpress.elasticsearch.IElasticSearch
import com.sksamuel.elastic4s.ElasticDsl._
import org.apache.commons.lang3.math.NumberUtils
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

/**
  * @author kostas.kougios
  *         Date: 15/09/16
  */
class SearchService @Inject()(elastic: IElasticSearch)
{
  def searchAll(q: String): Future[Seq[DmdIdentifiable]] = {

    if(NumberUtils.isNumber(q)){
      for {
        doc<- elastic.exec(
          get id q from "dmd"
        ).map {
          g=>
            if(g.isExists)
              Some(toDomain(g.getType,g.getSourceAsBytes))
            else None
        }
      } yield {
        doc match {
          case Some(dmd) => Seq(dmd)
          case None => freeTextSearch(q).await
        }
      }
    } else freeTextSearch(q)
  }

  private def freeTextSearch(q: String): Future[Seq[DmdIdentifiable]] = {
    elastic.exec(
      search in "dmd" types("amp", "vmp", "vmpp", "vtm", "ampp") query q size 10000
    ).map {
      r =>
        r.hits.map {
          hit =>
            toDomain(hit.`type`,hit.source)
        }
    }
  }

  private def toDomain(tpe:String,source:Array[Byte]) =
    tpe match {
      case "amp" => Json.fromJson[Amp](Json.parse(source)).get
      case "vmp" => Json.fromJson[Vmp](Json.parse(source)).get
      case "vmpp" => Json.fromJson[Vmpp](Json.parse(source)).get
      case "vtm" => Json.fromJson[Vtm](Json.parse(source)).get
      case "ampp" => Json.fromJson[Ampp](Json.parse(source)).get
    }
}
