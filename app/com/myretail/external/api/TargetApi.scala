package com.myretail.external.api

import javax.inject.Inject
import play.api.{Configuration, Logger}
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

trait TargetApi {
  def getProductTitle(id:Int):Future[String]
}

class TargetProductApi @Inject() (ws: WSClient,config:Configuration)(implicit ec:ExecutionContext) extends TargetApi {

  val logger = Logger(this.getClass)

  def getProductTitle(id:Int):Future[String] = {
    getRequest.get().map {
      response => (response.json \ "product" \ "item" \ "product_description" \ "title").as[String]
    }recover{
      case ex:Exception =>
        logger.error(s"getProductTitle error ",ex)
        s"${id}-UnableToRetrieve"
    }
  }

  private val getRequest:WSRequest = {
    val url = config.getOptional[String]("myretail.exernal.target.url").getOrElse("https://api.target.com/products/v3/")
    ws.url(url)
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("fields" -> "descriptions","id_type"->"TCIN","key"->"43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz")
      .withRequestTimeout(10000.millis)
  }

}
