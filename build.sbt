organization := "com.example"

name := "oppdrag-skall"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "net.databinder" %% "unfiltered-directives" % "0.8.0",
  "net.databinder" %% "unfiltered-filter" % "0.8.0",
  "net.databinder" %% "unfiltered-jetty" % "0.8.0",
  "com.jteigen" %% "linx" % "0.1"
)
