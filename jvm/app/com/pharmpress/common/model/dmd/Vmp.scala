package com.pharmpress.common.model.dmd

/**
 * Created by James on 14/01/2015.
 */
case class Vmp(
  id: String,
  validDate: Option[String] = None,
  idPrev: Option[String] = None,
  vtmId: Option[String] = None,
  invalid: Option[String] = None,
  name: String,
  abbrevName: Option[String] = None,
  basisOfName: String,
  nameDate: Option[String] = None,
  namePrev: Option[String] = None,
  basisOfNamePrev: Option[String] = None,
  nameChangeReason: Option[String] = None,
  combProd: Option[String] = None,
  presStatus: Option[String] = None,
  sugarFree: Option[String] = None,
  glutenFree: Option[String] = None,
  preservativeFree: Option[String] = None,
  cfcFree: Option[String] = None,
  nonAvailable: Option[String] = None,
  nonAvailableDate: Option[String] = None,
  dfInd: Option[String] = None,
  udfs: Option[String] = None,
  udfsUOM: Option[String] = None,
  unitDoseUOM: Option[String] = None,
  ontDrugForm: Option[Seq[OntDrugForm]] = None,
  drugForm: Option[DrugForm] = None,
  drugRoute: Option[Seq[DrugRoute]] = None,
  controlDrugInfo: ControlDrugInfo,
  virtualProductIngredients: Option[Seq[VirtualProductIngredient]] = None
) extends DmdIdentifiable

case class VirtualProductIngredient(
  vmpId: String,
  ingredientSubstance: Ingredient,
  basisStrength: Option[String] = None,
  basisSubstance: Option[Ingredient] = None,
  strengthNumeratorVal: Option[String] = None,
  strengthNumeratorUOM: Option[String] = None,
  strengthDenominatorVal: Option[String] = None,
  strengthDenominatorUOM: Option[String] = None
)

case class OntDrugForm(
  vmpId: String,
  form: String
)

case class DrugForm(
  vmpId: String,
  form: String
)

case class DrugRoute(
  vmpId: String,
  route: String
)

case class ControlDrugInfo(
  vmpId: String,
  category: String,
  categoryDate: Option[String] = None,
  categoryPrev: Option[String] = None
)
