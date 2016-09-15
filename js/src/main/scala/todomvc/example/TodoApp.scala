package todomvc.example

import com.greencatsoft.angularjs.Angular
import com.searchmvc.{SearchCtrl, SearchService}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
object TodoApp extends JSApp {

  override def main() {
    Angular.module("searchmvc")
      .controller[SearchCtrl]
      .factory[SearchService.Factory]
  }
}
