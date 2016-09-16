package todomvc.example

import javax.inject.{Inject, Singleton}

import com.pharmpress.common.model.dmd.IdTitle
import com.pharmpress.dmdbrowser.service.ContentService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import prickle.Pickle
import views.html.index

@Singleton
class DmdController @Inject() (contentService: ContentService) extends Controller {

  def home() = Action {
    Ok(index())
  }

  def vtm(id: Long) = Action.async {

    contentService.getVtm(id.toString).map {
      _.fold(BadRequest(s"Couldn't find VTM with id $id"))(vtm => Ok(views.html.vtm(vtm)))
    }
  }

  def vmp(id: Long) = Action.async {

    contentService.getVmp(id.toString).map {
      _.fold(BadRequest(s"Couldn't find VMP with id $id"))(vmp => Ok(views.html.vmp(vmp)))
    }
  }

  def amp(id: Long) = Action.async {

    contentService.getAmp(id.toString).map {
      _.fold(BadRequest(s"Couldn't find AMP with id $id"))(amp => Ok(views.html.amp(amp)))
    }
  }

  def vmpp(id: Long) = Action.async {
    contentService.getVmpp(id.toString).map {
      _.fold(BadRequest(s"Couldn't find VMPP with id $id"))(vmpp => Ok(views.html.vmpp(vmpp)))
    }
  }

  def ampp(id: Long) = Action.async {
    contentService.getAmpp(id.toString).map {
      _.fold(BadRequest(s"Couldn't find AMPP with id $id"))(ampp => Ok(views.html.ampp(ampp)))
    }
  }

  def ampsByVmpParent(vmpId: String) = Action.async {

    contentService.getAmpsByVmpParent(vmpId).map { amps => Ok(Pickle.intoString(amps.map(amp => IdTitle(amp.id, amp.desc)))) }
  }

  def vmppsByVmpParent(vmpId: String) = Action.async {

    contentService.getVmppsByVmpParent(vmpId).map { amps => Ok(Pickle.intoString(amps.map(vmpp => IdTitle(vmpp.id, vmpp.name)))) }
  }

  def vmpsByVtmParent(vtmId: String) = Action.async {

    contentService.getVmpsByVtmParent(vtmId).map { vmps => Ok(Pickle.intoString(vmps.map(vmp => IdTitle(vmp.id, vmp.name)))) }
  }

  def amppsByAmpParent(ampId: String) = Action.async {

    contentService.getAmppsByAmpParent(ampId).map { amps => Ok(Pickle.intoString(amps.map(ampp => IdTitle(ampp.id, ampp.name)))) }
  }
}
