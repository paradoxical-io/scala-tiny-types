package com.devshorts

import java.io.{File, FileInputStream, StringReader}

import com.devshorts.traits.LiveReader

case class ProgramArguments(definitionsFile : String = null)

object Main extends App {
  implicit val weekDaysRead: scopt.Read[TypeAliasType.Value] = scopt.Read.reads(TypeAliasType withName)

  val parser = new scopt.OptionParser[ProgramArguments]("tiny-types") {
    head("tiny-types", "1.0")
    opt[String]('d', "definitionsFile") required() action {
      (value, config) => config copy (definitionsFile = value)
    } text "The definitions file"
  }

  parser.parse(args, ProgramArguments()) match {
    case Some(c) => {
      println(s"Using $c")

      val config: Config = Config.apply(new FileInputStream(c.definitionsFile))

      new Runner(config.typeGroups)
          with LiveReader run()
    }
    case None    => ()
  }
}
