package com.pharmpress.common.model.dmd

/**
 * Created by James on 16/01/2015.
 */
case class Vtm(
  id: String,
  name: String,
  invalid: Option[String],
  abbrevName: Option[String],
  prevId: Option[String],
  validDate: Option[String]
) extends DmdIdentifiable
