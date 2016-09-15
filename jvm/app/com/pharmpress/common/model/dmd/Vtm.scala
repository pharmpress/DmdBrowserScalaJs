package com.pharmpress.common.model.dmd

/**
 * Created by James on 16/01/2015.
 */
case class Vtm(
  id: String,
  name: String,
  invalid: Option[String] = None,
  abbrevName: Option[String] = None,
  prevId: Option[String] = None,
  validDate: Option[String] = None
) extends DmdIdentifiable
