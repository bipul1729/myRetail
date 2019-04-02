package com.myretail.forms

import play.api.libs.json.{Format, Json}

case class ProductForm(id:Option[Int] = None,name:Option[String] = None,current_price:Option[PriceDataForm])
object ProductForm {
  implicit val productFormFormat: Format[ProductForm] = Json.format[ProductForm]
}