package com.searchmvc

import com.greencatsoft.angularjs.core.{HttpService, Location, Timeout}
import com.greencatsoft.angularjs.{AbstractController, AngularExecutionContextProvider, injectable}
import org.scalajs.dom._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}

/**
  * Created by mesfin on 15/09/16.
  */
@JSExport
@injectable("searchCtrl")
class SearchCtrl(
  scope: SearchScope,
  location: Location,
  http: HttpService,
  val timeout: Timeout
) extends AbstractController[SearchScope](scope) with AngularExecutionContextProvider
{

  @JSExport
  def search(query: String) = {
    console.info(s"search $query")
    http.get[Array[SearchResult]]("/search/"+query).onComplete {
      case Success(results) => {
        scope.results = results.toJSArray
      }
      case Failure(t) => handleError(t)
    }
  }

  private def handleError(t: Throwable) {
    console.error(s"An error has occurred: '$t'.")

    t.printStackTrace()
  }
}