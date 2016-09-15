package com.searchmvc

import com.greencatsoft.angularjs
import com.greencatsoft.angularjs.core.{HttpService, Timeout}
import com.greencatsoft.angularjs.{AngularExecutionContextProvider, Service, injectable}
import prickle.Unpickle
import todomvc.example.HttpClientSupport

import scala.concurrent.Future
import scala.scalajs.js.JSON

/**
  * Created by mesfin on 15/09/16.
  */

@injectable("searchService")
class SearchService(val http: HttpService, val timeout: Timeout)
  extends HttpClientSupport with AngularExecutionContextProvider with Service {
  require(http != null, "Missing argument 'http'.")
  require(timeout != null, "Missing argument 'timeout'.")

  def searchDmd(query: String): Future[Seq[SearchResult]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/search/" + query)
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[SearchResult]].fromString(_))
  }
}

object SearchService {

  @injectable("searchService")
  class Factory(http: HttpService, timeout: Timeout) extends angularjs.Factory[SearchService] {

    def apply(): SearchService = new SearchService(http, timeout)
  }
}