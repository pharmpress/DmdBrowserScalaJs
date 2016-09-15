package com.pharmpress.elasticsearch

import com.sksamuel.elastic4s.source.DocumentSource
import play.api.libs.json.{JsValue, Json}

class PlaySource(root: JsValue) extends DocumentSource {
  def json = Json.stringify(root)
}

object PlaySource {
  def apply(root: JsValue) = new PlaySource(root)
}