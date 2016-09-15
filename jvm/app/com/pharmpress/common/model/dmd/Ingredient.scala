package com.pharmpress.common.model.dmd

/**
 * Created by James on 16/01/2015.
 */
case class Ingredient(
  isId: String,
  name: String,
  isIdDate: Option[String],
  isIdPrev: Option[String],
  invalid: Option[String]
)
