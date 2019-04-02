package com.myretail.services

import com.myretail.forms.ProductForm

import scala.concurrent.Future

trait ProductService {

  def getProductById(id:Int):Future[Product]

  def updateProductPrice(product:ProductForm):Future[Product]

}
