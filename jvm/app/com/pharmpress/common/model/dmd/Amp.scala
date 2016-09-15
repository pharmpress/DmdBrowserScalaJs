package com.pharmpress.common.model.dmd

/**
 * Created by James on 14/01/2015
 */
case class Amp(
  id: String,
  invalid: Option[String] = None,
  name: String,
  abbrevName: Option[String] = None,
  desc: String,
  nameDate: Option[String] = None,
  namePrev: Option[String] = None,
  licAuth: String,
  licAuthChange: Option[String] = None,
  licAuthDate: Option[String] = None,
  combProd: Option[String] = None,
  flavour: Option[String] = None,
  ema: Option[String] = None,
  parallelImport: Option[String] = None,
  availRestrict: String,
  vmpId: String,
  vtmId: Option[String] = None,
  supplier: String,
  actualProductIngredient: Option[Seq[ActualProductIngredient]] = None,
  applianceProductInformation: Option[ApplianceProductInformation] = None,
  licensedRoute: Option[Seq[LicensedRoute]] = None,
  tradeFamilyGroupId: Option[String] = None,
  tradeFamilyId: Option[String] = None
) extends DmdIdentifiable

case class ActualProductIngredient(
  ampId: String,
  ingredientSubstance: Ingredient,
  strength: Option[String] = None,
  unitOfMeasure: Option[String] = None
)

case class LicensedRoute(ampId: String, route: String)

case class ApplianceProductInformation(
  ampId: String,
  sizeWeight: Option[String] = None,
  colour: Option[String] = None,
  prodOrderNo: Option[String] = None
)
