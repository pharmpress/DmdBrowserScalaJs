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
  def init(vmpId: String) = {
    console.log("VMP id is " + vmpId)
    dmdService.amps(vmpId) onComplete {
      case Success(amps) => scope.amps = amps.toJSArray
      case Failure(t) => handleError(t)
    }
  }

  private def handleError(t: Throwable) {
    console.error(s"An error has occurred: '$t'.")

    t.printStackTrace()
  }
}