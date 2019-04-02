package com.myretail.controllers

import javax.inject.Inject
import play.api.Logger
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

class ProductController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  val logger = Logger(this.getClass)

  def getById(id: Int) = Action.async { implicit request=>
    Future.successful(Ok(com.myretail.views.html.index()))
  }

  def updateProductPriceById(id: Int) = Action.async((parse.json)) { implicit request=>
    Future.successful(Ok(com.myretail.views.html.index()))
  }

}
