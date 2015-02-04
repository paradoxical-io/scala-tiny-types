name := "tiny-types"

organization := "com.devshorts"

version := "1.0"

scalaVersion := "2.11.5"

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies ++= Seq {
  "com.github.scopt" %% "scopt" % "3.3.0"
}

libraryDependencies ++= Seq{
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
}

    