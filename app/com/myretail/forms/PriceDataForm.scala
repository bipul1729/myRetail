package com.myretail.forms
import play.api.libs.json.{Format, Json, Writes}

case class PriceDataForm (value:Double,currency_code:String)
object PriceDataForm {
  implicit val priceDataFormFormat: Format[PriceDataForm] = Json.format[PriceDataForm]
}
