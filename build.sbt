organization := "no.penger"

name := "oppdrag-skall"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.4"

val unfiltered = Seq(
  "net.databinder" %% "unfiltered-filter" % "0.8.0",
  "net.databinder" %% "unfiltered-jetty" % "0.8.0",
  "com.jteigen" %% "linx" % "0.1"
)

lazy val constrettoDeps         = Seq(
  "org.constretto"              %  "constretto-core"        % "2.0.2",
  "org.constretto"              %  "constretto-api"         % "2.0.2",
  "org.constretto"              %% "constretto-scala"       % "1.0",
  "org.ini4j"                   %  "ini4j"                  % "0.5.2",
  "com.thoughtworks.paranamer"  %  "paranamer"              % "2.3")

val logging  = Seq(
  "com.typesafe" %% "scalalogging-slf4j" % "1.0.1",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-log4j12" % "1.7.5",
  "org.slf4j" % "jul-to-slf4j" % "1.7.5",
  "org.slf4j" % "jcl-over-slf4j" % "1.7.5"
)

libraryDependencies ++= unfiltered ++ logging ++ constrettoDeps

