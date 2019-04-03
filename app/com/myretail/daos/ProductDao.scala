package com.myretail.daos

import com.myretail.models.ProductItem
import javax.inject.Inject
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import play.api.Logger
import play.api.libs.json.Json
import com.myretail.models.ProductItem._

import scala.concurrent.{ExecutionContext, Future}

trait ProductDao {

  def find(id:Int):Future[Option[ProductItem]]
  def save(productItem:ProductItem):Future[ProductItem]
  def update(id:Int, productItem:ProductItem):Future[ProductItem]
}

class MongoProductDao @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec:ExecutionContext) extends ProductDao {

  val logger = Logger(this.getClass)

  private  def productItems: Future[JSONCollection] =
    reactiveMongoApi.database.map(_.collection[JSONCollection]("products"))
  override def find(id: Int): Future[Option[ProductItem]] = productItems.flatMap(_.find(Json.obj("id" -> id)).one[ProductItem])

  override def save(ProductItem: ProductItem): Future[ProductItem] = productItems.flatMap(_.insert.one(ProductItem)).map { lastError =>
    logger.debug(s"Successfully inserted with LastError: $lastError")
    ProductItem
  }

  override def update(id:Int,ProductItem: ProductItem): Future[ProductItem] = productItems.flatMap(_.update(Json.obj("id" -> id),ProductItem)).map { lastError =>
    logger.debug(s"Successfully updated with LastError: $lastError")
    ProductItem
  }
}
