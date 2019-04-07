package com.myretail.controllers

import com.myretail.daos.TitleDao
import com.myretail.forms.{ProductForm, ProductTitleForm}
import com.myretail.models.ProductTitle
import com.myretail.services.ProductService
import javax.inject.Inject
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class MockTitleController @Inject()(cc: ControllerComponents,productTileDao:TitleDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  val logger = Logger(this.getClass)

  def getById(id: Int) = Action.async { implicit request =>
    productTileDao.find(id).map{
        case Some(pt) =>Ok(Json.toJson(pt))
        case None => Ok(Json.toJson(s"Id ${id} not found"))
      }.recover {
        case ex: IllegalArgumentException => Ok(Json.toJson(ex.getMessage))
        case ex: Exception => Ok(Json.toJson(ex.getMessage))
      }
  }

  def updateProductTitleById(id: Int) = Action.async((parse.json)) { implicit request=>
    request.body.validate[ProductTitleForm].fold(
      { errors =>
        Future(BadRequest(Json.toJson(s"Invalid product field in Update form ${errors}")))
      }, {
        pf => {
          productTileDao.find(id) flatMap{
            case Some(pt) =>
                productTileDao.update(id, pt.copy(name = pf.name)).map(x => Created(Json.toJson(x))
              ).recover {
                case ex: Exception => Ok(Json.toJson(ex.getMessage))
              }
            case None =>
              val productTitle = ProductTitle(id,pf.name)
              productTileDao.save(productTitle).map(x => Created(Json.toJson(x))
              ).recover {
                case ex: Exception => Ok(Json.toJson(ex.getMessage))
              }
          }
        }
      })
  }
}

