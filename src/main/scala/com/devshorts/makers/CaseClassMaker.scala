package com.devshorts.makers

import java.io.{BufferedWriter, File, FileWriter}

import com.devshorts._
import com.devshorts.parsers.TinyTypeDefinition
import com.devshorts.traits._



case class ParsedTinyType(caseClass : String, conversion : String)

object CaseClassMaker {
  def process(d: TinyTypeDefinition): ParsedTinyType = {
    d match {
      case TinyTypeDefinition(tinyName, typeName) =>

        val definition = s"case class $tinyName(data : $typeName) extends AnyVal"
        val conversion = s"implicit def convert${tinyName}(i : $tinyName) : $typeName = i.data"

        ParsedTinyType(definition, conversion)
    }
  }
}

abstract class CaseClassMaker(c: Config, definitions: Seq[TinyTypeDefinition]) extends TinyMaker with OutputProvider {

  def make(): Writer = {
    val parsed : Seq[ParsedTinyType] = definitions.map(CaseClassMaker.process)

    val tinyTemplates =  parsed.map(_.caseClass).mkString(System.lineSeparator())

    val tinyConversions = withObjectConversions(parsed.map(_.conversion).mkString(System.lineSeparator()))

    new Writer {
      override def write(): Unit = {
        val writer = getOutputProvider()

        writer.write(tinyTemplates, "TinyTypes.scala")
        writer.write(tinyConversions, "TinyConversions.scala")
      }
    }
  }

  def withObjectConversions(content: String) = {
    s"""
object Conversions{
${content.split(System.lineSeparator()).map(i => "    " + i.trim).mkString(System.lineSeparator())}
}""".trim
  }

}
