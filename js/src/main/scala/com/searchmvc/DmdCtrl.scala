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
  def loadAmpsAndVmpps(dmd: String, vmpId: String) = {

    val futureAmps = dmdService.amps(dmd, vmpId)
    val futureVmpps = dmdService.vmpps(dmd, vmpId)

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
  def loadVmps(dmd: String, vtmId: String) = {
    console.log("VTM id is " + vtmId)
    dmdService.vmps(dmd, vtmId) onComplete {
      case Success(vmps) => scope.vmps = vmps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadVmpps(dmd: String, vmpId: String) = {
    console.log("VMP id is " + vmpId)
    dmdService.vmpps(dmd, vmpId) onComplete {
      case Success(vmpps) => scope.vmpps = vmpps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadAmps(dmd: String, vmpId: String) = {
    console.log("VMP id is " + vmpId)
    dmdService.amps(dmd, vmpId) onComplete {
      case Success(amps) => scope.amps = amps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadAmpsForTradeFamily(dmd: String, tfId: String) = {
    console.log("TF id is " + tfId)
    dmdService.ampsForTradeFamily(dmd, tfId) onComplete {
      case Success(amps) => scope.amps = amps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadAmpsForTradeFamilyGroup(dmd: String, tfgId: String) = {
    console.log("TFG id is " + tfgId)
    dmdService.ampsForTradeFamilyGroup(dmd, tfgId) onComplete {
      case Success(amps) => scope.amps = amps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  @JSExport
  def loadAmpps(dmd: String, ampId: String) = {
    console.log("AMP id is " + ampId)
    dmdService.ampps(dmd, ampId) onComplete {
      case Success(amps) => scope.ampps = amps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  private def handleError(t: Throwable) {
    console.error(s"An error has occurred: '$t'.")

    t.printStackTrace()
  }
}