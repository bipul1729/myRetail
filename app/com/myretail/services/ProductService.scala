package com.myretail.services

import com.myretail.daos.ProductDao
import com.myretail.external.api.TargetApi
import com.myretail.forms.ProductForm
import javax.inject.Inject
import play.api.Logger
import com.myretail.forms._
import com.myretail.models.{PriceData, ProductItem}

import scala.concurrent.{ExecutionContext, Future}

trait ProductService {

  def getProductById(id:Int):Future[ProductItem]

  def updateProductPrice(id:Int, productForm:ProductForm):Future[ProductItem]

}

class ProductServiceImpl @Inject()(productDao:ProductDao,targetApi:TargetApi)(implicit ec:ExecutionContext) extends ProductService {
  val logger = Logger(this.getClass)

  override def getProductById(id: Int): Future[ProductItem] = {
    targetApi.getProductTitle(id).flatMap(title =>
      productDao.find(id) map{
        case Some(p) => p.copy(name = title)
        case None =>
          val msg = s"ProductItem id ${id} not found in db ."
          logger.debug(msg)
          throw new IllegalArgumentException(msg)
      }
    )
  }

  override def updateProductPrice(id:Int, productForm:ProductForm): Future[ProductItem] = {
    val priceData = PriceData(productForm.current_price.value,productForm.current_price.currency_code)
    productDao.find(id) flatMap {
      case Some(p) =>
        val productItem = p.copy(current_price = priceData)
        productDao.update(id,productItem)
      case None =>
        logger.debug(s"Product with id ${id} not found. Inserting ne one")
        val productItem = ProductItem(id,"NA",priceData)
        productDao.save(productItem)
    }
  }
}
