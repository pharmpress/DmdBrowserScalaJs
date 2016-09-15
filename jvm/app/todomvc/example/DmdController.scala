package todomvc.example

import javax.inject.{Inject, Singleton}

import com.pharmpress.common.model.dmd.{Amp, ControlDrugInfo, Ingredient, LicensedRoute, VirtualProductIngredient, Vmp, Vtm}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import views.html.index

@Singleton
class DmdController @Inject() extends Controller {

  def home() = Action {
    Ok(index())
  }

  def vtm(id: Long) = Action {
    val vtm = Vtm(
      id = "123",
      name = "VTM",
      invalid = None,
      abbrevName = Some("V"),
      prevId = None,
      validDate = None
    )
    Ok(views.html.vtm(vtm))
  }

  def vmp(id: Long) = Action {
    val vmp = Vmp(
      id = "456",
      vtmId = Some("123"),
      name = "VMP",
      controlDrugInfo = ControlDrugInfo(
        vmpId = "456",
        category = "cat"
      ),
      basisOfName = "bon",
      invalid = None,
      virtualProductIngredients = Some(Seq(
        VirtualProductIngredient(
          "1", Ingredient(isId = "2", name = "ingredient"), Some("5"), Some(Ingredient(isId="3", name="fish")), Some("100"), Some("mg"), Some("200"), Some("kg")
        ),
        VirtualProductIngredient(
          "1", Ingredient(isId = "2", name = "ingredient"), Some("5"), Some(Ingredient(isId="3", name="fish")), Some("100"), Some("mg"), Some("200"), Some("kg")
        )
      ))
    )
    Ok(views.html.vmp(vmp))
  }

  def amp(id: Long) = Action {
    val amp = Amp("123456", None, "amp", None, "", None, None, "", None, None, None, None, None, None, "", "",
      None, "", None, None, Some(Seq(LicensedRoute("123456", "route1"), LicensedRoute("123456", "route2"))), None, None)
    Ok(views.html.amp(amp))
  }

}
