package com.myretail.daos


import com.myretail.models.{ProductItem, ProductTitle}
import javax.inject.Inject
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import play.api.Logger
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}
import com.myretail.models.ProductTitle._

trait TitleDao {

  def find(id:Int):Future[Option[ProductTitle]]
  def save(productTitle:ProductTitle):Future[ProductTitle]
  def update(id:Int, productTitle:ProductTitle):Future[ProductTitle]
}

class MongoTitleDao @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec:ExecutionContext) extends TitleDao {

  val logger = Logger(this.getClass)

  private  def productTiltes: Future[JSONCollection] =
    reactiveMongoApi.database.map(_.collection[JSONCollection]("titles"))

  override def find(id: Int): Future[Option[ProductTitle]] = productTiltes.flatMap(_.find(Json.obj("id" -> id)).one[ProductTitle])

  override def save(productTitle: ProductTitle): Future[ProductTitle] = productTiltes.flatMap(_.insert.one(productTitle)).map { lastError =>
    logger.debug(s"Successfully inserted with LastError: $lastError")
    productTitle
  }

  override def update(id: Int, productTitle: ProductTitle): Future[ProductTitle] = productTiltes.flatMap(_.update(Json.obj("id" -> id),productTitle)).map { lastError =>
    logger.debug(s"Successfully updated with LastError: $lastError")
    productTitle
  }
}
