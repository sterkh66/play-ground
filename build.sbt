name := """play-scala-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.12")

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies ++= Seq(
  jdbc,
  "org.playframework.anorm" %% "anorm" % "2.6.2",
  "org.xerial" % "sqlite-jdbc" % "3.25.2"
)
