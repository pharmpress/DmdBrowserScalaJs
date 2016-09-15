package com.pharmpress.common.model.dmd

/**
 * Created by James on 14/01/2015.
 */
case class Vmpp(
  id: String,
  invalid: Option[String],
  name: String,
  vmpId: String,
  vtmId: Option[String],
  quantityVal: String,
  quantityUOM: String,
  combPack: Option[String],
  drugTariffInfo: Option[DrugTariffInfo]
) extends DmdIdentifiable

object Vmpp {
  lazy val base = Vmpp(
    id = "vmppId",
    invalid = None,
    name = "vmpp name",
    vmpId = "vmpId",
    vtmId = None,
    quantityVal = "30",
    quantityUOM = "tablet",
    combPack = None,
    drugTariffInfo = Some(DrugTariffInfo.base)
  )
}

case class DrugTariffInfo(
  vmppId: String,
  paymentCategory: String,
  price: Option[String],
  date: Option[String],
  prevPrice: Option[String]
)

object DrugTariffInfo {
  lazy val base = DrugTariffInfo(
    vmppId = "vmppId",
    paymentCategory = "payment category",
    price = Some("1000"),
    date = None,
    prevPrice = None
  )

}
