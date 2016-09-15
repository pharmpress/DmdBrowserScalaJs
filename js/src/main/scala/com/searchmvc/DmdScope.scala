package com.searchmvc

import com.greencatsoft.angularjs.core.Scope
import com.pharmpress.common.model.dmd.{IdTitle}
import microjson.JsObject

import scala.scalajs.js

/**
  * Created by jimbo on 15/09/16.
  */
@js.native
trait DmdScope extends Scope {

  var vmps: js.Array[IdTitle] = js.native
  var vmpps: js.Array[IdTitle] = js.native
  var amps: js.Array[IdTitle] = js.native
  var ampps: js.Array[IdTitle] = js.native
}
