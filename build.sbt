
lazy val buildSettings = Seq(
  organization := "com.dallaway",
  scalaVersion := "2.12.2",
  version := "1.0.0"
)

lazy val commonSettings = Seq(
  scalacOptions := Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-unchecked",
      "-deprecation",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps",
      "-Ywarn-dead-code",
      "-Ywarn-value-discard",
      "-Xlint",
      "-Xfatal-warnings"
  ),
  libraryDependencies ++= Seq(
    "org.scalacheck" %%% "scalacheck" % "1.13.4" % "test",
    "org.scalatest"  %%% "scalatest"  % "3.0.1"  % "test"
  )
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val fsm = crossProject.in(file("."))
  .settings(name := "fsm")
  .settings(buildSettings ++ commonSettings)

lazy val fsmJVM = fsm.jvm
lazy val fsmJS = fsm.js

