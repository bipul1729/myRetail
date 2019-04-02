package com.myretail.services
import com.myretail.forms.ProductForm
import javax.inject.Inject

import scala.concurrent.Future

class ProductServiceImpl @Inject() () extends ProductService {

  override def getProductById(id: Int): Future[Product] = ???

  override def updateProductPrice(product: ProductForm): Future[Product] = ???
}
