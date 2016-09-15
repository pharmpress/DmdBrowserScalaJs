package com.pharmpress.common.model.dmd

import java.time.LocalDateTime
import java.util.Base64

import play.api.libs.json.Json

/**
 * @author kostas.kougios
 *         Date: 08/09/16
 */
case class ReportFile(name: String, data: String, time: LocalDateTime) {
  def dataRaw = Base64.getDecoder.decode(data)
}

object ReportFile {
  implicit val format = Json.format[ReportFile]
}
