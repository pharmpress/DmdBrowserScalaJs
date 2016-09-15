package todomvc.example

import javax.inject.{Inject, Singleton}

import com.pharmpress.common.model.dmd.{ControlDrugInfo, Ingredient, VirtualProductIngredient, Vmp, Vtm}
import com.pharmpress.dmdbrowser.service.ContentService
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
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

}
