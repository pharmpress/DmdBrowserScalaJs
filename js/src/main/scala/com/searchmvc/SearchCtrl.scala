//package com.searchmvc
//
//import java.security.Provider.Service
//
//import com.greencatsoft.angularjs.{AbstractController, AngularExecutionContextProvider, injectable}
//import org.scalajs.dom.Location
//import todomvc.example.TodoScope
//
//import scala.scalajs.js
//import scala.scalajs.js.annotation.JSExport
//import scala.scalajs.js.Dynamic.literal
//import scala.scalajs.js.JSConverters._
//import scala.scalajs.js.annotation.JSExport
//import scala.util.{ Failure, Success }
//
///**
//  * Created by mesfin on 15/09/16.
//  */
//@JSExport
//@injectable("searchCtrl")
//class SearchCtrl(scope: SearchScope,
//                 location: Location,
//                 service: SearchServiceProxy,
//                 query: String) extends AbstractController[SearchScope](scope) with AngularExecutionContextProvider {
//
//  scope.query = js.native
//
//  service.searchDmd(query).onComplete {
//case Success(task) =>
//
//  }
//}