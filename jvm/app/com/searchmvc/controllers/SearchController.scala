package com.searchmvc.controllers

import com.searchmvc.SearchResult
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

/**
  * Created by jimbo on 15/09/16.
  */
class SearchController extends Controller {

  implicit lazy val formatSearchResult = Json.format[SearchResult]

  def home() = Action {
    Ok(views.html.search())
  }

  def search(query: String) = Action.async {

    val results = (1 to 100).map { num =>
      SearchResult(num.toString, "Result " + num)
    }

    Future.successful(Ok(Json.toJson(results)))
  }
}
