ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "drone_generation"
  )
