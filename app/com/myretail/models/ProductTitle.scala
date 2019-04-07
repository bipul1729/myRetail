package com.myretail.models

import play.api.libs.json._

case class ProductTitle(id: Int, name:String)

object ProductTitle {
  //implicit val productItemFormat: Format[ProductItem] = Json.format[ProductItem]
  implicit val titleDataWrite: OWrites[ProductTitle] = Json.writes[ProductTitle]
  implicit val titleDataReads: Reads[ProductTitle] = Json.reads[ProductTitle]
}
