ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-client" % "3.3.2",
  "org.apache.kafka" % "kafka-clients" % "3.1.0",
  "net.liftweb" %% "lift-json" % "3.5.0"
)

lazy val root = (project in file("."))
  .settings(
    name := "report-storage"
  )
