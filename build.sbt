import sbt.Keys._

name := "tiny-types"

organization := "io.paradoxical"

version := "1.0"

scalaVersion := "2.11.5"

resolvers += Resolver.sonatypeRepo("public")

crossScalaVersions := Seq("2.10.0", "2.11.0")

libraryDependencies ++= Seq {
  "com.github.scopt" %% "scopt" % "3.3.0"
}

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.2"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.7.2"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.2"



libraryDependencies ++= Seq{
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
}

pgpPassphrase := Some(sys.env.getOrElse("GPG_PASSWORD", default = "").toArray)
