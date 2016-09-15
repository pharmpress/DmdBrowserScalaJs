package com.pharmpress.common.model.dmd

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by mesfin on 15/09/16.
  */
@JSExportAll
case class ClientAmp(
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
//                      actualProductIngredient: Seq[ClientActualProductIngredient] = Seq.empty,
                      applianceProductInformation: Option[ClientApplianceProductInformation] = None,
                      licensedRoute: Seq[ClientLicensedRoute] = Seq.empty,
                      tradeFamilyGroupId: Option[String] = None,
                      tradeFamilyId: Option[String] = None
                    )

//@JSExportAll
//case class ClientActualProductIngredient(
//                                    ampId: String,
//                                    ingredientSubstance: ClientIngredient,
//                                    strength: Option[String] = None,
//                                    unitOfMeasure: Option[String] = None
//                                  )

@JSExportAll
case class ClientIngredient(
                       isId: String,
                       name: String,
                       isIdDate: Option[String] = None,
                       isIdPrev: Option[String] = None,
                       invalid: Option[String] = None
                     )

@JSExportAll
case class ClientLicensedRoute(ampId: String, route: String)

@JSExportAll
case class ClientApplianceProductInformation(
//                                        ampId: Option[String] = None,
                                        sizeWeight: Option[String] = None,
                                        colour: Option[String] = None,
                                        prodOrderNo: Option[String] = None
                                      )
