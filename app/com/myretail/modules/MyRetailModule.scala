package com.myretail.modules

import com.google.inject.AbstractModule
import com.myretail.daos.{MongoProductDao, ProductDao}
import com.myretail.external.api.{TargetApi, TargetProductApi}
import com.myretail.services.{ProductService, ProductServiceImpl}
import net.codingwell.scalaguice.ScalaModule

class MyRetailModule extends AbstractModule with ScalaModule {

  override  def configure() {
    bind[ProductService].to[ProductServiceImpl]
    bind[ProductDao].to[MongoProductDao]
    bind[TargetApi].to[TargetProductApi]

  }
}

