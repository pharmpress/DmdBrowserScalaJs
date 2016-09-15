package com.searchmvc.controllers

import javax.inject.Inject

import com.pharmpress.common.model.dmd.{Amp, Ampp, Vmp, Vtm}
import com.pharmpress.dmdbrowser.service.SearchService
import com.searchmvc.SearchResult
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import prickle.Pickle

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
        Pickle.intoString(
          results.map(d => SearchResult(d.id, d.name, d match {
            case _: Amp => "amp"
            case _: Vmp => "vmp"
            case _: Vtm => "vtm"
            case _: Ampp => "ampp"
          }))
        )
      )
    }
  }
}
