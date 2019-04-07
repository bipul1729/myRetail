package com.myretail.controllers

import com.myretail.external.api.TargetApi
import com.myretail.forms.ProductForm
import com.myretail.models.ProductItem
import com.myretail.services.ProductService
import javax.inject.Inject
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

class ProductController @Inject()(cc: ControllerComponents,
                                  productService: ProductService,
                                  targetApi:TargetApi)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  val logger = Logger(this.getClass)

  def getById(id: Int) = Action.async { implicit request=>

    val cll = com.myretail.controllers.routes.MockTitleController.getById(id)
    targetApi.getProductTitleMock(id,cll.absoluteURL()).flatMap { name =>
      productService.getPriceById(id)
        .map { x =>
          val pItem = ProductItem(id, name, x.current_price)
          Ok(Json.toJson(pItem))
        }.recover {
        case ex: IllegalArgumentException => Ok(Json.toJson(ex.getMessage))
        case ex: Exception => Ok(Json.toJson(ex.getMessage))
      }
    }.recover{
      case ex:IllegalArgumentException => Ok(Json.toJson(ex.getMessage))
      case ex:Exception => Ok(Json.toJson(ex.getMessage))
    }
  }

  def updateProductPriceById(id: Int) = Action.async((parse.json)) { implicit request=>
    request.body.validate[ProductForm].fold(
      { errors =>
        Future(BadRequest(Json.toJson(s"Invalid product field in Update form ${errors}")))
      }, {
        pf => {
          productService.updateProductPrice(id, pf)
         .map(x=> Created(Json.toJson(x)))
            .recover {
            case ex: Exception => Ok(Json.toJson(ex.getMessage))
          }
        }
      })
  }

}
