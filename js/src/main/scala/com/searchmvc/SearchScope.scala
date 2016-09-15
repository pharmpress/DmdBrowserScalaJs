package com.searchmvc

import com.greencatsoft.angularjs.core.Scope

import scala.scalajs.js

/**
  * Created by mesfin on 15/09/16.
  */

@js.native
trait SearchScope extends Scope {

  var results: js.Array[SearchResult] = js.native
}