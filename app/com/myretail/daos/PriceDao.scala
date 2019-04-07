package com.myretail.daos

import com.myretail.models.{ProductPrice}
import javax.inject.Inject
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import play.api.Logger
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}
import com.myretail.models.ProductPrice._

trait PriceDao {

  def find(id:Int):Future[Option[ProductPrice]]
  def save(productPrice:ProductPrice):Future[ProductPrice]
  def update(id:Int, productPrice:ProductPrice):Future[ProductPrice]
}

class MongoPriceDao @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec:ExecutionContext) extends PriceDao {

  val logger = Logger(this.getClass)

  private  def productPrices: Future[JSONCollection] =
    reactiveMongoApi.database.map(_.collection[JSONCollection]("prices"))

  override def find(id: Int): Future[Option[ProductPrice]]  =
    productPrices.flatMap(_.find(Json.obj("id" -> id)).one[ProductPrice])

  override def save(productPrice: ProductPrice): Future[ProductPrice] = productPrices.flatMap(_.insert.one(productPrice)).map { lastError =>
    logger.debug(s"Successfully inserted with LastError: $lastError")
    productPrice
  }

  override def update(id: Int, productPrice: ProductPrice): Future[ProductPrice] = productPrices.flatMap(_.update(Json.obj("id" -> id),productPrice)).map { lastError =>
    logger.debug(s"Successfully updated with LastError: $lastError")
    productPrice
  }
}
