package com.myretail.models

import play.api.libs.json.{Format, Json, OWrites, Reads}

case class ProductItem(id: Int, name:String, current_price:PriceData)
object ProductItem {
  //implicit val productItemFormat: Format[ProductItem] = Json.format[ProductItem]
  implicit val productItemWrite: OWrites[ProductItem] = Json.writes[ProductItem]
  implicit val productItemReads: Reads[ProductItem] = Json.reads[ProductItem]
}
