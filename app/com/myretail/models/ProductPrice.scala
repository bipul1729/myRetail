package com.myretail.models

import play.api.libs.json.{Json, OWrites, Reads}

case class ProductPrice(id: Int,current_price:PriceData)


object ProductPrice {
  //implicit val productItemFormat: Format[ProductItem] = Json.format[ProductItem]
  implicit val ProductPriceWrite: OWrites[ProductPrice] = Json.writes[ProductPrice]
  implicit val ProductPriceReads: Reads[ProductPrice] = Json.reads[ProductPrice]
}
