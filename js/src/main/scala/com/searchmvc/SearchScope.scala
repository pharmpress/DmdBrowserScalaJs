package com.searchmvc

import com.greencatsoft.angularjs.core.Scope
import scala.scalajs.js
import scala.scalajs.js.Dictionary

/**
  * Created by mesfin on 15/09/16.
  */

@js.native
trait SearchScope extends Scope {

  var query: String = js.native
}