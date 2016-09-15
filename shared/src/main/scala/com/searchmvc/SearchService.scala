package com.searchmvc

import scala.concurrent.Future

/**
  * Created by mesfin on 15/09/16.
  */
trait SearchService {

  def searchDmd(query: String): Future[Seq[SearchResult]]
}