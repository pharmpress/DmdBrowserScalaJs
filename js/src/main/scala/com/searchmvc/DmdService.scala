package com.searchmvc

import com.greencatsoft.angularjs
import com.greencatsoft.angularjs.core.{HttpService, Timeout}
import com.greencatsoft.angularjs.{AngularExecutionContextProvider, Service, injectable}
import com.pharmpress.common.model.dmd.{IdTitle}
import microjson.{JsObject, JsValue}
import prickle.Unpickle
import todomvc.example.HttpClientSupport

import scala.concurrent.Future
import scala.scalajs.js.JSON

/**
  * Created by jimbo on 15/09/16.
  */
@injectable("dmdService")
class DmdService(val http: HttpService, val timeout: Timeout)
  extends HttpClientSupport with AngularExecutionContextProvider with Service {
  require(http != null, "Missing argument 'http'.")
  require(timeout != null, "Missing argument 'timeout'.")

  def vmps(dmd: String, vtmId: String): Future[Seq[IdTitle]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/" + dmd + "/vmps/" + vtmId)
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[IdTitle]].fromString(_))
  }

  def vmpps(dmd: String, vmpId: String): Future[Seq[IdTitle]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/" + dmd + "/vmpps/" + vmpId)
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[IdTitle]].fromString(_))
  }

  def amps(dmd: String, vmpId: String): Future[Seq[IdTitle]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/" + dmd + "/amps/" + vmpId)
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[IdTitle]].fromString(_))
  }

  def ampsForTradeFamily(dmd: String, tfId: String): Future[Seq[IdTitle]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/" + dmd + "/ampsFromTf/" + tfId)
        .map(JSON.stringify(_))
        .map(Unpickle[Seq[IdTitle]].fromString(_))
  }

  def ampsForTradeFamilyGroup(dmd: String, tfgId: String): Future[Seq[IdTitle]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/" + dmd + "/ampsFromTfg/" + tfgId)
        .map(JSON.stringify(_))
        .map(Unpickle[Seq[IdTitle]].fromString(_))
  }

  def ampps(dmd: String, ampId: String): Future[Seq[IdTitle]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/" + dmd + "/ampps/" + ampId)
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[IdTitle]].fromString(_))
  }
}

object DmdService {

  @injectable("dmdService")
  class Factory(http: HttpService, timeout: Timeout) extends angularjs.Factory[DmdService] {

    def apply(): DmdService = new DmdService(http, timeout)
  }
}
