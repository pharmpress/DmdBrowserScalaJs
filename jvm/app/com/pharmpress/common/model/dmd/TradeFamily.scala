package com.pharmpress.common.model.dmd

/**
 * Record a trade family (from the SNOMED CT UK drug extension).
 *
 * Created by jonathan stott on 05/05/2015.
 */
case class TradeFamily(
  id: String,
  name: String,
  groupId: Option[String]
) extends DmdIdentifiable
