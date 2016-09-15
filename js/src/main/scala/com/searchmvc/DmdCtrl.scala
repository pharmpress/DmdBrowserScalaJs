package com.searchmvc

import com.greencatsoft.angularjs.core.{Location, Timeout}
import com.greencatsoft.angularjs.{AbstractController, AngularExecutionContextProvider, injectable}
import org.scalajs.dom._

import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}

/**
  * Created by mesfin on 15/09/16.
  */
@JSExport
@injectable("dmdCtrl")
class DmdCtrl(
  scope: DmdScope,
  location: Location,
  dmdService: DmdService,
  val timeout: Timeout
) extends AbstractController[DmdScope](scope) with AngularExecutionContextProvider
{

  @JSExport
  def loadAmpsAndVmpps(vmpId: String) = {

    val futureAmps = dmdService.amps(vmpId)
    val futureVmpps = dmdService.vmpps(vmpId)

    futureAmps onComplete {
      case Success(amps) => scope.amps = amps.toJSArray
      case Failure(t) => handleError(t)
    }

    futureVmpps onComplete {
      case Success(vmpps) => scope.vmpps = vmpps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadVmps(vtmId: String) = {
    console.log("VTM id is " + vtmId)
    dmdService.vmps(vtmId) onComplete {
      case Success(vmps) => scope.vmps = vmps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadVmpps(vmpId: String) = {
    console.log("VMP id is " + vmpId)
    dmdService.vmpps(vmpId) onComplete {
      case Success(vmpps) => scope.vmpps = vmpps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadAmps(vmpId: String) = {
    console.log("VMP id is " + vmpId)
    dmdService.amps(vmpId) onComplete {
      case Success(amps) => scope.amps = amps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadAmpps(ampId: String) = {
    console.log("AMP id is " + ampId)
    dmdService.ampps(ampId) onComplete {
      case Success(amps) => scope.ampps = amps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  private def handleError(t: Throwable) {
    console.error(s"An error has occurred: '$t'.")

    t.printStackTrace()
  }
}