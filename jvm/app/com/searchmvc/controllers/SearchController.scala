package com.searchmvc.controllers

import javax.inject.Inject

import com.pharmpress.dmdbrowser.service.SearchService
import com.searchmvc.SearchResult
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by jimbo on 15/09/16.
  */
class SearchController @Inject()(searchService: SearchService) extends Controller
{

  implicit lazy val formatSearchResult = Json.format[SearchResult]

  def home() = Action {
    Ok(views.html.search())
  }

  def search(query: String) = Action.async {

    for {
      results <- searchService.searchAll(query)
    } yield {
      Ok(
        Json.toJson(
          results.map(dmdObj => SearchResult(dmdObj.id, dmdObj.name))
        )
      )
    }
  }
}
