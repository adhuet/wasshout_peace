ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.1.0"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.30"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.30"

lazy val root = (project in file("."))
  .settings(
    name := "drone_generation"
  )
