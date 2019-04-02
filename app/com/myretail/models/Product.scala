package com.myretail.models

import play.api.libs.json.{Format, Json}

case class Product (id: Int,name:String,current_price:PriceData)
object Product {
  implicit val productFormat: Format[Product] = Json.format[Product]
}
