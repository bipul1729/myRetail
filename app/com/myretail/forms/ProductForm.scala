package com.myretail.forms

import com.myretail.models.{PriceData, ProductItem}
import play.api.libs.json.{Format, Json}

case class ProductForm(id:Option[Int] = None, name:Option[String] = None, current_price:PriceDataForm)
object ProductForm {
  implicit val ProductFormFormat: Format[ProductForm] = Json.format[ProductForm]
}



case class ProductTitleForm(id:Option[Int] = None, name:String)
object ProductTitleForm {
  implicit val productTitleFormFormat: Format[ProductTitleForm] = Json.format[ProductTitleForm]
}