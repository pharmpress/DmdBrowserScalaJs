package com.searchmvc

import com.greencatsoft.angularjs.core.Scope
import com.pharmpress.common.model.dmd.ClientAmp
import microjson.JsObject

import scala.scalajs.js

/**
  * Created by jimbo on 15/09/16.
  */
@js.native
trait DmdScope extends Scope {

  var amps: js.Array[ClientAmp] = js.native
}
