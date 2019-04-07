package com.myretail.services

import com.myretail.daos.{PriceDao, TitleDao}
import com.myretail.external.api.TargetApi
import com.myretail.forms.ProductForm
import javax.inject.Inject
import play.api.Logger
import com.myretail.forms._
import com.myretail.models.{PriceData, ProductItem, ProductPrice}

import scala.concurrent.{ExecutionContext, Future}

trait ProductService {

  def getPriceById(id:Int):Future[ProductPrice]

  def updateProductPrice(id:Int, productForm:ProductForm):Future[ProductPrice]

}

class ProductServiceImpl @Inject()(priceDao:PriceDao,
                                   titleDao:TitleDao)(implicit ec:ExecutionContext) extends ProductService {
  val logger = Logger(this.getClass)

  override def getPriceById(id: Int): Future[ProductPrice] = {
      priceDao.find(id) map{
        case Some(p) => p
        case None =>
          val msg = s"ProductItem id ${id} price is not found in db ."
          logger.debug(msg)
          throw new IllegalArgumentException(msg)
      }

  }

  override def updateProductPrice(id:Int, productForm:ProductForm): Future[ProductPrice] = {
    val priceData = PriceData(productForm.current_price.value,productForm.current_price.currency_code)
    priceDao.find(id) flatMap {
      case Some(p) =>
        val productPrice = p.copy(current_price = priceData)
        priceDao.update(id,productPrice)
      case None =>
        logger.debug(s"Product Price with id ${id} not found.")
        val productPrice = ProductPrice(id,priceData)
        priceDao.save(productPrice)
    }
  }
}
