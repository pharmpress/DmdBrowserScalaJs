package com.pharmpress.dmdbrowser.service

import javax.inject.Inject

import com.pharmpress.common.model.dmd.{Amp, Ampp, TradeFamily, TradeFamilyGroup, Vmp, Vmpp, Vtm}
import com.pharmpress.elasticsearch.IElasticSearch
import play.api.libs.json.Reads
import com.sksamuel.elastic4s.ElasticDsl._

import scala.concurrent.Future

/**
  * Created by jimbo on 15/09/16.
  */
class ContentService @Inject()(elastic: IElasticSearch) {

  def getVtm(id: String): Future[Option[Vtm]] = getDocById[Vtm]("vtm", id)
  def getVmp(id: String): Future[Option[Vmp]] = getDocById[Vmp]("vmp", id)
  def getVmpp(id: String): Future[Option[Vmpp]] = getDocById[Vmpp]("vmpp", id)
  def getAmp(id: String): Future[Option[Amp]] = getDocById[Amp]("amp", id)
  def getAmpsByVmpParent(vmpId: String): Future[Seq[Amp]] = elastic.searchForDocsAsync[Amp]("dmd", "amp", must {
    termQuery("vmpId", vmpId)
  })
  def getVmppsByVmpParent(vmpId: String) = elastic.searchForDocsAsync[Vmpp]("dmd", "vmpp", must {
    termQuery("vmpId", vmpId)
  })
  def getVmpsByVtmParent(vmpId: String): Future[Seq[Vmp]] = elastic.searchForDocsAsync[Vmp]("dmd", "vmp", must {
    termQuery("vtmId", vmpId)
  })
  def getAmppsByAmpParent(ampId: String) = elastic.searchForDocsAsync[Ampp]("dmd", "ampp", must {
    termQuery("ampId", ampId)
  })
  def getAmpp(id: String): Future[Option[Ampp]] = getDocById[Ampp]("ampp", id)
  def getTf(id: String): Future[Option[TradeFamily]] = getDocById[TradeFamily]("tf", id)
  def getTfg(id: String): Future[Option[TradeFamilyGroup]] = getDocById[TradeFamilyGroup]("tfg", id)

  private def getDocById[T](docType: String, id: String)(implicit reads: Reads[T]): Future[Option[T]] = {
    elastic.getDocByIdAsync[T]("dmd", docType, id)
  }
}
