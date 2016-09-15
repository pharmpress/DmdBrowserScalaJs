package com.searchmvc

import com.greencatsoft.angularjs
import com.greencatsoft.angularjs.core.{HttpService, Timeout}
import com.greencatsoft.angularjs.{AngularExecutionContextProvider, Service, injectable}
import com.pharmpress.common.model.dmd.ClientAmp
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

  def amps(vmpId: String): Future[Seq[ClientAmp]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/amps/" + vmpId)
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[ClientAmp]].fromString(_))
  }
}

object DmdService {

  @injectable("dmdService")
  class Factory(http: HttpService, timeout: Timeout) extends angularjs.Factory[DmdService] {

    def apply(): DmdService = new DmdService(http, timeout)
  }
}
