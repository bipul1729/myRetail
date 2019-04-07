package com.myretail.modules

import com.google.inject.AbstractModule
import com.myretail.daos.{MongoPriceDao, MongoTitleDao, PriceDao, TitleDao}
import com.myretail.external.api.{TargetApi, TargetProductApi}
import com.myretail.services.{ProductService, ProductServiceImpl}
import net.codingwell.scalaguice.ScalaModule

class MyRetailModule extends AbstractModule with ScalaModule {

  override  def configure() {
    bind[ProductService].to[ProductServiceImpl]
    bind[PriceDao].to[MongoPriceDao]
    bind[TitleDao].to[MongoTitleDao]
    bind[TargetApi].to[TargetProductApi]

  }
}

