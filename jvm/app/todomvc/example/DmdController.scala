package todomvc.example

import javax.inject.{Inject, Singleton}

import com.pharmpress.common.model.dmd.IdTitle
import com.pharmpress.dmdbrowser.service.ContentService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import prickle.Pickle
import views.html.index

@Singleton
class DmdController @Inject() (contentService: ContentService) extends Controller {

  def home() = Action {
    Ok(index())
  }

  def vtm(dmd: String, id: Long) = Action.async {
    contentService.getVtm(dmd, id.toString).map {
      _.fold(BadRequest(s"Couldn't find VTM with id $id"))(vtm => Ok(views.html.vtm(dmd, vtm)))
    }
  }

  def vmp(dmd: String, id: Long) = Action.async {
    contentService.getVmp(dmd, id.toString).map { v =>
      v.fold(BadRequest(s"Couldn't find VMP with id $id"))(vmp => Ok(views.html.vmp(dmd, vmp)))
    }
  }

  def amp(dmd: String, id: Long) = Action.async {
    contentService.getAmp(dmd, id.toString).map {
      _.fold(BadRequest(s"Couldn't find AMP with id $id"))(amp => Ok(views.html.amp(dmd, amp)))
    }
  }

  def vmpp(dmd: String, id: Long) = Action.async {
    contentService.getVmpp(dmd, id.toString).map {
      _.fold(BadRequest(s"Couldn't find VMPP with id $id"))(vmpp => Ok(views.html.vmpp(dmd, vmpp)))
    }
  }

  def ampp(dmd: String, id: Long) = Action.async {
    contentService.getAmpp(dmd, id.toString).map {
      _.fold(BadRequest(s"Couldn't find AMPP with id $id"))(ampp => Ok(views.html.ampp(dmd, ampp)))
    }
  }

  def tf(dmd: String, id: Long) = Action.async {
    contentService.getTf(dmd, id.toString).map {
      _.fold(BadRequest(s"Couldn't find TF with id $id"))(tf => Ok(views.html.tf(dmd, tf)))
    }
  }

  def tfg(dmd: String, id: Long) = Action.async {
    contentService.getTfg(dmd, id.toString).map {
      _.fold(BadRequest(s"Couldn't find TFG with id $id"))(tfg => Ok(views.html.tfg(dmd, tfg)))
    }
  }

  def ampsByVmpParent(dmd: String, vmpId: String) = Action.async {
    contentService.getAmpsByVmpParent(dmd, vmpId).map { amps => Ok(Pickle.intoString(amps.map(amp => IdTitle(amp.id, amp.desc)))) }
  }

  def ampsByTradeFamily(dmd: String, tfId: String) = Action.async {
    contentService.getAmpsByTradeFamily(dmd, tfId).map { amps => Ok(Pickle.intoString(amps.map(amp => IdTitle(amp.id, amp.desc)))) }
  }

  def ampsByTradeFamilyGroup(dmd: String, tfgId: String) = Action.async {
    contentService.getAmpsByTradeFamilyGroup(dmd, tfgId).map { amps => Ok(Pickle.intoString(amps.map(amp => IdTitle(amp.id, amp.desc)))) }
  }

  def vmppsByVmpParent(dmd: String, vmpId: String) = Action.async {
    contentService.getVmppsByVmpParent(dmd, vmpId).map { amps => Ok(Pickle.intoString(amps.map(vmpp => IdTitle(vmpp.id, vmpp.name)))) }
  }

  def vmpsByVtmParent(dmd: String, vtmId: String) = Action.async {
    contentService.getVmpsByVtmParent(dmd, vtmId).map { vmps => Ok(Pickle.intoString(vmps.map(vmp => IdTitle(vmp.id, vmp.name)))) }
  }

  def amppsByAmpParent(dmd: String, ampId: String) = Action.async {
    contentService.getAmppsByAmpParent(dmd, ampId).map { amps => Ok(Pickle.intoString(amps.map(ampp => IdTitle(ampp.id, ampp.name)))) }
  }

  def tradeFamiliesByTradeFamilyGroup(dmd: String, tfgId: String) = Action.async {
    contentService.getTradeFamiliesByTradeFamilyGroup(dmd, tfgId).map { tfs => Ok(Pickle.intoString(tfs.map(tf => IdTitle(tf.id, tf.name)))) }
  }

}
