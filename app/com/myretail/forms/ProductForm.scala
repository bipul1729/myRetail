package com.myretail.forms

import com.myretail.models.{PriceData, ProductItem}
import play.api.libs.json.{Format, Json}

case class ProductForm(id:Option[Int] = None, name:Option[String] = None, current_price:Option[PriceDataForm])
object ProductForm {

  implicit class ProductItemUpdateOps(ProductItemForm: ProductForm) {

    def toProductItem(id:Int): ProductItem = {
      val priceData = PriceData(34,"Rouble")
      ProductItem(id = id,name = "xyz",current_price = priceData)
    }
  }

  implicit val ProductFormFormat: Format[ProductForm] = Json.format[ProductForm]
}