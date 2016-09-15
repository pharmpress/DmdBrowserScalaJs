package com.pharmpress.common.model

import play.api.libs.json.{ Format, Json }
import org.cvogt.play.json.Jsonx
//import org.cvogt.play.json.implicits.optionWithNull

//import org.cvogt.play.json.implicits.optionWithNull

/**
 * Created by James on 16/01/2015.
 */
package object dmd {

  implicit val vtmWrites = Json.format[Vtm]

  /** play-json Reads/Writes/Format doesn't work with case class of >22 parameters */
  implicit val ingWrites = Json.format[Ingredient]
  implicit val vpiWrites = Json.format[VirtualProductIngredient]
  implicit val ontWrites = Json.format[OntDrugForm]
  implicit val dformWrites = Json.format[DrugForm]
  implicit val drouteWrites = Json.format[DrugRoute]
  implicit val cdiWrites = Json.format[ControlDrugInfo]
  implicit def vmpWrites = Jsonx.formatCaseClass[Vmp]
  implicit val tradeFamilyGroupWrites = Json.format[TradeFamilyGroup]
  implicit val tradeFamilyWrites = Json.format[TradeFamily]

  implicit val dtiWrites = Json.format[DrugTariffInfo]
  implicit val vmppWrites = Json.format[Vmpp]

  implicit val apIngWrites = Json.format[ActualProductIngredient]
  implicit val lrouteWrites = Json.format[LicensedRoute]
  implicit val apInfoWrites = Json.format[ApplianceProductInformation]
  implicit def ampWrites = Jsonx.formatCaseClass[Amp]
  implicit val dmdConceptUpdatedWrites = Json.format[DmdConceptUpdated]

  implicit val aPackInfoWrites = Json.format[AppliancePackInformation]
  implicit val dppiWrites = Json.format[DrugProductPrescribInfo]
  implicit val mppWrites = Json.format[MedicinalProductPrice]
  implicit val reimInfoWrites = Json.format[ReimbursementInfo]
  implicit val amppWrites = Json.format[Ampp]

  implicit val ccWrites = Json.format[CombContent]
}
