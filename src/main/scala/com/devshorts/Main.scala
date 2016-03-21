package com.devshorts

import com.devshorts.traits.LiveReader

object Main extends App {
  implicit val weekDaysRead: scopt.Read[TypeAliasType.Value] = scopt.Read.reads(TypeAliasType withName)

  val parser = new scopt.OptionParser[Config]("tiny-types") {
    head("tiny-types", "1.0")
    opt[String]('d', "definitionsFile") required() action {
      (value, config) => config copy (definitionsFile = value)
    } text "The comma separated definitions file"

    opt[String]('o', "outputPackage") required() action {
      (value, config) => config copy (outputPackage = value)
    } text "The target output package of the format a.b.c.d"

    opt[TypeAliasType.Value]('t', "formatType") optional() action {
      (value, config) => config copy (creationType = value)
    } text "The format type to create the types in"

    opt[String]('r', "rootFolder") optional() action {
      (value, config) => config copy (rootFolder = value)
    } text "Optional root folder. If not supplied current folder will be used"

    note(
          """

Tiny types generate case classes of the form

    case class Foo(wrapped : Type)

Along with a 'dereferencer' type

     object Conversions{
         implicit def convertType(f : Foo) : Type = f.data
     }""")
  }

  parser.parse(args, Config()) match {
    case Some(c) => {
      println(s"Using $c")

      new Runner(c)
          with LiveReader run()
    }
    case None    => ()
  }
}
