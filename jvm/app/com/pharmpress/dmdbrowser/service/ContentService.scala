package com.pharmpress.dmdbrowser.service

import javax.inject.Inject

import com.pharmpress.common.model.dmd.{Vtm, Vmp, Vmpp, Amp, Ampp}
import com.pharmpress.elasticsearch.IElasticSearch
import play.api.libs.json.Reads

import scala.concurrent.Future

/**
  * Created by jimbo on 15/09/16.
  */
class ContentService @Inject()(elastic: IElasticSearch) {

  def getVtm(id: String): Future[Option[Vtm]] = getDocById[Vtm]("vtm", id)
  def getVmp(id: String): Future[Option[Vmp]] = getDocById[Vmp]("vmp", id)
  def getVmpp(id: String): Future[Option[Vmpp]] = getDocById[Vmpp]("vmpp", id)
  def getAmp(id: String): Future[Option[Amp]] = getDocById[Amp]("amp", id)
  def getAmpp(id: String): Future[Option[Ampp]] = getDocById[Ampp]("ampp", id)

  private def getDocById[T](docType: String, id: String)(implicit reads: Reads[T]): Future[Option[T]] = {
    elastic.getDocByIdAsync[T]("dmd", docType, id)
  }
}
