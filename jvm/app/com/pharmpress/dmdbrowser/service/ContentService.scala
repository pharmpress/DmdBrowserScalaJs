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

  def getVtm(dmd: String, id: String): Future[Option[Vtm]] = getDocById[Vtm](dmd, "vtm", id)

  def getVmp(dmd: String, id: String): Future[Option[Vmp]] = getDocById[Vmp](dmd, "vmp", id)

  def getVmpp(dmd: String, id: String): Future[Option[Vmpp]] = getDocById[Vmpp](dmd, "vmpp", id)

  def getAmp(dmd: String, id: String): Future[Option[Amp]] = getDocById[Amp](dmd, "amp", id)

  def getAmpsByVmpParent(dmd: String, vmpId: String): Future[Seq[Amp]] = elastic.searchForDocsAsync[Amp](dmd, "amp", must {
    termQuery("vmpId", vmpId)
  })

  def getAmpsByTradeFamily(dmd: String, tfId: String): Future[Seq[Amp]] = elastic.searchForDocsAsync[Amp](dmd, "amp", must {
    termQuery("tfId", tfId)
  })

  def getAmpsByTradeFamilyGroup(dmd: String, tfId: String): Future[Seq[Amp]] = elastic.searchForDocsAsync[Amp](dmd, "amp", must {
    termQuery("tfgId", tfId)
  })

  def getVmppsByVmpParent(dmd: String, vmpId: String) = elastic.searchForDocsAsync[Vmpp](dmd, "vmpp", must {
    termQuery("vmpId", vmpId)
  })

  def getVmpsByVtmParent(dmd: String, vmpId: String): Future[Seq[Vmp]] = elastic.searchForDocsAsync[Vmp](dmd, "vmp", must {
    termQuery("vtmId", vmpId)
  })

  def getAmppsByAmpParent(dmd: String, ampId: String) = elastic.searchForDocsAsync[Ampp](dmd, "ampp", must {
    termQuery("ampId", ampId)
  })

  def getAmpp(dmd: String, id: String): Future[Option[Ampp]] = getDocById[Ampp](dmd, "ampp", id)

  def getTf(dmd: String, id: String): Future[Option[TradeFamily]] = getDocById[TradeFamily](dmd, "tf", id)

  def getTfg(dmd: String, id: String): Future[Option[TradeFamilyGroup]] = getDocById[TradeFamilyGroup](dmd, "tfg", id)

  def getTradeFamiliesByTradeFamilyGroup(dmd: String, tfgId: String): Future[Seq[TradeFamily]] =
    elastic.searchForDocsAsync[TradeFamily](dmd, "tf", must {
      termQuery("tfgId", tfgId)
    })

  private def getDocById[T](dmd: String, docType: String, id: String)(implicit reads: Reads[T]): Future[Option[T]] = {
    elastic.getDocByIdAsync[T](dmd, docType, id)
  }

}
