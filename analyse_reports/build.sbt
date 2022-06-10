ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.8"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-sql_2.11" % "2.2.0",
  "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % "2.2.0",
  "org.apache.hadoop" % "hadoop-client" % "3.3.2",
  "net.liftweb" %% "lift-json" % "3.5.0"
)

lazy val root = (project in file("."))
  .settings(
    name := "analyse_reports"
  )
