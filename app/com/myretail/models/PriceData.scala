package com.myretail.models

import play.api.libs.json.{Format, Json, Writes}

case class PriceData (value:Double,currency_code:String)
object PriceData {
  implicit val priceDataFormat: Format[PriceData] = Json.format[PriceData]
}
