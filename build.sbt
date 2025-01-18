val scala3Version = "3.6.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-agent",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "com.typesafe.akka" %% "akka-actor" % "2.6.21",
      "ch.qos.logback" % "logback-classic" % "1.4.11",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
       "com.typesafe.akka" %% "akka-testkit" % "2.6.21" % Test
    )
  )
