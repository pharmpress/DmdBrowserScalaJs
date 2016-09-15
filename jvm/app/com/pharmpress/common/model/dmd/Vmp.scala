package com.pharmpress.common.model.dmd

/**
 * Created by James on 14/01/2015.
 */
case class Vmp(
  id: String,
  validDate: Option[String],
  idPrev: Option[String],
  vtmId: Option[String],
  invalid: Option[String],
  name: String,
  abbrevName: Option[String],
  basisOfName: String,
  nameDate: Option[String],
  namePrev: Option[String],
  basisOfNamePrev: Option[String],
  nameChangeReason: Option[String],
  combProd: Option[String],
  presStatus: Option[String],
  sugarFree: Option[String],
  glutenFree: Option[String],
  preservativeFree: Option[String],
  cfcFree: Option[String],
  nonAvailable: Option[String],
  nonAvailableDate: Option[String],
  dfInd: Option[String],
  udfs: Option[String],
  udfsUOM: Option[String],
  unitDoseUOM: Option[String],
  ontDrugForm: Option[Seq[OntDrugForm]],
  drugForm: Option[DrugForm],
  drugRoute: Option[Seq[DrugRoute]],
  controlDrugInfo: ControlDrugInfo,
  virtualProductIngredients: Option[Seq[VirtualProductIngredient]]
) extends DmdIdentifiable

case class VirtualProductIngredient(
  vmpId: String,
  ingredientSubstance: Ingredient,
  basisStrength: Option[String],
  basisSubstance: Option[Ingredient],
  strengthNumeratorVal: Option[String],
  strengthNumeratorUOM: Option[String],
  strengthDenominatorVal: Option[String],
  strengthDenominatorUOM: Option[String]
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
  categoryDate: Option[String],
  categoryPrev: Option[String]
)
