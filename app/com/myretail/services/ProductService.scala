package com.myretail.services

import com.myretail.daos.ProductDao
import com.myretail.forms.ProductForm
import javax.inject.Inject
import play.api.Logger
import com.myretail.forms._
import com.myretail.models.ProductItem

import scala.concurrent.{ExecutionContext, Future}

trait ProductService {

  def getProductById(id:Int):Future[ProductItem]

  def updateProductPrice(id:Int, productForm:ProductForm):Future[ProductItem]

}

class ProductServiceImpl @Inject()(productDao:ProductDao)(implicit ec:ExecutionContext) extends ProductService {
  val logger = Logger(this.getClass)

  override def getProductById(id: Int): Future[ProductItem] = {
    productDao.find(id) map{
      case Some(p) => p
      case None =>
        val msg = s"ProductItem id ${id} not found in db ."
        logger.debug(msg)
        throw new IllegalArgumentException(msg)

    }
  }

  override def updateProductPrice(id:Int, productForm:ProductForm): Future[ProductItem] = {
    productDao.find(id) flatMap {
      case Some(p) =>
        val productItem = productForm.toProductItem(id)
        productDao.update(id,productItem)
      case None =>
        logger.debug(s"Product with id ${id} not found. Inserting ne one")
        val productItem = productForm.toProductItem(id)
        productDao.save(productItem)
    }
  }
}
