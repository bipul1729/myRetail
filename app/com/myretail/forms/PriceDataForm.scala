package com.myretail.forms
import play.api.libs.json.{Format, Json, Writes}

case class PriceDataForm (value:Option[Double],currency_code:Option[String])
object PriceDataForm {
  implicit val priceDataFormFormat: Format[PriceDataForm] = Json.format[PriceDataForm]
}
