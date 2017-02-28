package com.pharmpress.dmdbrowser.service

import javax.inject.Inject

import com.pharmpress.common.model.dmd.{Amp, Ampp, DmdIdentifiable, TradeFamily, Vmp, Vmpp, Vtm}
import com.pharmpress.elasticsearch.IElasticSearch
import com.sksamuel.elastic4s.ElasticDsl._
import org.apache.commons.lang3.math.NumberUtils
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import collection.JavaConverters._

/**
  * @author kostas.kougios
  *         Date: 15/09/16
  */
class SearchService @Inject()(elastic: IElasticSearch)
{
  def searchAll(dmd: String, q: String): Future[Seq[DmdIdentifiable]] = {

    if(NumberUtils.isNumber(q)){
      for {
        doc<- elastic.exec(
          get id q from dmd
        ).map {
          g=>
            if(g.isExists)
              Some(toDomain(g.getType,g.getSourceAsBytes))
            else None
        }
      } yield {
        doc match {
          case Some(dmdDoc) => Seq(dmdDoc)
          case None => freeTextSearch(dmd, q).await
        }
      }
    } else freeTextSearch(dmd, q)
  }

  def getPossibleForm(dmd: String, q: String): Future[Option[String]] = {
    elastic.exec(
      search in dmd -> "vmp" query {
        must {
          matchQuery("drugForm.form", q)
        }
      } fields "drugForm.form" size 1
    ).map {
      _.hits.map {
        _.fields("drugForm.form").getValue[String]
      }.headOption
    }
  }

  private def freeTextSearch(dmd: String, q: String): Future[Seq[DmdIdentifiable]] = {
    elastic.exec(
      search in dmd types("amp", "vmp", "vmpp", "vtm", "ampp", "tf") query q size 10000
    ).map {
      r =>
        r.hits.map {
          hit =>
            toDomain(hit.`type`,hit.source)
        }
    }
  }

  def dmdIndexes(): Future[Seq[String]] = {
    elastic.getClient.execute(get segments "dmd_2*").map(_.getIndices.keySet().asScala.toSeq.sorted.reverse)
  }

  private def toDomain(tpe:String,source:Array[Byte]) =
    tpe match {
      case "amp" => Json.fromJson[Amp](Json.parse(source)).get
      case "vmp" => Json.fromJson[Vmp](Json.parse(source)).get
      case "vmpp" => Json.fromJson[Vmpp](Json.parse(source)).get
      case "vtm" => Json.fromJson[Vtm](Json.parse(source)).get
      case "ampp" => Json.fromJson[Ampp](Json.parse(source)).get
      case "tf" => Json.fromJson[TradeFamily](Json.parse(source)).get
    }
}
