package com.myretail.external.api

import javax.inject.Inject
import play.api.{Configuration, Logger}
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.mvc.Call

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

trait TargetApi {
  def getProductTitle(id:Int):Future[String]

  def getProductTitleMock(id:Int,url: String):Future[String]
}

class TargetProductApi @Inject() (ws: WSClient,config:Configuration)(implicit ec:ExecutionContext) extends TargetApi {

  val logger = Logger(this.getClass)

  override def getProductTitle(id:Int):Future[String] = {
    getRequest.get().map {
      response => (response.json \ "product" \ "item" \ "product_description" \ "title").as[String]
    }recover{
      case ex:Exception =>
        logger.error(s"getProductTitle error ",ex)
        s"${id}-UnableToRetrieve"
    }
  }
  override def getProductTitleMock(id:Int,url: String):Future[String] = {

    getRequestMock(url).get() map {
      response => (response.json \ "name").as[String]
    }recover{
      case ex:Exception =>
        logger.error(s"getProductTitle error ",ex)
        s"${id}-UnableToMockRetrieve"
    }
  }

  private def getRequestMock(url : String ):WSRequest = {
    ws.url(url)
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("fields" -> "descriptions","id_type"->"TCIN","key"->"43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz")
      .withRequestTimeout(10000.millis)
  }

  private val getRequest:WSRequest = {
    val url = config.getOptional[String]("myretail.exernal.target.url").getOrElse("https://api.target.com/products/v3/")
    ws.url(url)
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("fields" -> "descriptions","id_type"->"TCIN","key"->"43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz")
      .withRequestTimeout(10000.millis)
  }

}
