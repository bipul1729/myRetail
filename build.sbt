name := """myRetail"""
organization := "com.target"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "org.reactivemongo" % "play2-reactivemongo_2.11" % "0.16.5-play27"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
libraryDependencies += "com.iheart" %% "ficus" % "1.4.1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.target.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.target.binders._"
