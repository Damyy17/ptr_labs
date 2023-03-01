name := "ptr_labs"

version := "0.1"

scalaVersion := "3.2.2"

val AkkaVersion = "2.7.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.5"