package com.pharmpress.common.model.dmd

/**
 * Record a trade family group (from the SNOMED CT UK drug extension).
 *
 * Created by jonathan stott on 05/05/2015.
 */
case class TradeFamilyGroup(
  id: String,
  name: String
) extends DmdIdentifiable
