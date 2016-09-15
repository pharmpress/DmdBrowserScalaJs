package com.pharmpress.common.model.dmd

/**
 * Created by James on 14/01/2015.
 */
case class Ampp(
  id: String,
  invalid: Option[String],
  name: String,
  abbrevName: Option[String],
  ampId: String,
  vmppId: String,
  vmpId: String,
  vtmId: Option[String],
  combPack: Option[String],
  legalCategory: Option[String],
  subPackInfo: Option[String],
  discontinued: Option[String],
  discontinuedDate: Option[String],
  appliancePackInformation: Option[AppliancePackInformation],
  drugProductPrescribInfo: Option[DrugProductPrescribInfo],
  medicinalProductPrice: Option[MedicinalProductPrice],
  reimbursementInfo: Option[ReimbursementInfo]
) extends DmdIdentifiable

object Ampp {
  lazy val base = Ampp(
    id = "amppId",
    invalid = None,
    name = " ampp name",
    abbrevName = None,
    ampId = "ampId",
    vmppId = "vmppId",
    vmpId = "vmpId",
    vtmId = None,
    combPack = None,
    legalCategory = Some("legal category"),
    subPackInfo = None,
    discontinued = None,
    discontinuedDate = None,
    appliancePackInformation = None,
    drugProductPrescribInfo = None,
    medicinalProductPrice = Some(MedicinalProductPrice.base),
    reimbursementInfo = Some(ReimbursementInfo.base)
  )
}

case class AppliancePackInformation(
  amppId: String,
  reimbStatus: String,
  reimbStatusDate: Option[String],
  reimbStatusPrev: Option[String],
  packOrderNo: Option[String]
)

case class DrugProductPrescribInfo(
  amppId: String,
  schedule2: Option[String],
  acbs: Option[String],
  padm: Option[String],
  fp10Mda: Option[String],
  schedule1: Option[String],
  hospital: Option[String],
  nurseFormulary: Option[String],
  extendedNurseFormulary: Option[String],
  dentistFormulary: Option[String]
)

case class MedicinalProductPrice(
  amppId: String,
  price: Option[String],
  priceDate: Option[String],
  pricePrev: Option[String],
  priceBasis: String
)

object MedicinalProductPrice {
  lazy val base = MedicinalProductPrice(
    amppId = "amppId",
    price = Some("1000"),
    priceDate = None,
    pricePrev = None,
    priceBasis = "price basis"
  )
}

case class ReimbursementInfo(
  amppId: String,
  prescripCharges: Option[String],
  dispFees: Option[String],
  brokenBulk: Option[String],
  calendarPack: Option[String],
  specialContainer: Option[String],
  dnd: Option[String],
  fp34d: Option[String]
)

object ReimbursementInfo {
  lazy val base = ReimbursementInfo(
    amppId = "amppId",
    prescripCharges = Some("1"),
    dispFees = None,
    brokenBulk = None,
    calendarPack = None,
    specialContainer = None,
    dnd = None,
    fp34d = None
  )
}
/**
 * Also used by VMPP
 * @param parentId
 * @param childId
 */
case class CombContent(
  parentId: String,
  childId: String
)

