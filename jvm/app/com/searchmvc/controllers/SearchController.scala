package com.searchmvc.controllers

import javax.inject.Inject

import com.pharmpress.common.model.dmd.{Amp, Ampp, TradeFamily, Vmp, Vmpp, Vtm}
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

  def home() = Action.async {
    for {
      indexes <- searchService.dmdIndexes()
    } yield {
      Ok(views.html.search(indexes))
    }
  }

  def search(dmd: String, query: String) = Action.async {
    for {
      results <- searchService.searchAll(dmd, query)
    } yield {
      Ok(
        Pickle.intoString(
          results.map(d => SearchResult(d.id, d.name, d match {
            case _: Amp => "amp"
            case _: Vmp => "vmp"
            case _: Vmpp => "vmpp"
            case _: Vtm => "vtm"
            case _: Ampp => "ampp"
            case _: TradeFamily => "tf"
          }))
        )
      )
    }
  }
}
