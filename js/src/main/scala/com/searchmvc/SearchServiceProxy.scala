package com.searchmvc

import com.greencatsoft.angularjs
import com.greencatsoft.angularjs.core.{HttpService, Timeout}
import com.greencatsoft.angularjs.{AngularExecutionContextProvider, Service, injectable}
import prickle.Unpickle
import todomvc.example.{HttpClientSupport, Task, TaskService}

import scala.concurrent.Future
import scala.scalajs.js.{Date, JSON}

/**
  * Created by mesfin on 15/09/16.
  */

@injectable("searchService")
class SearchServiceProxy(val http: HttpService, val timeout: Timeout) extends SearchService
  with HttpClientSupport with AngularExecutionContextProvider with Service {
  require(http != null, "Missing argument 'http'.")
  require(timeout != null, "Missing argument 'timeout'.")

  override def searchDmd(query: String): Future[Seq[SearchResult]] = flatten {
    // Append a timestamp to prevent some old browsers from caching the result.
    httpGet("/search", "query" -> query)
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[SearchResult]].fromString(_))
  }
}

object SearchServiceProxy {

  @injectable("searchService")
  class Factory(http: HttpService, timeout: Timeout)
    extends angularjs.Factory[TaskService] {

    override def apply(): SearchServiceProxy = new SearchServiceProxy(http, timeout)
  }
}