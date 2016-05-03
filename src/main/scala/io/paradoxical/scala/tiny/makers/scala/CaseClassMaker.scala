package io.paradoxical.scala.tiny.makers.scala

import io.paradoxical.scala.tiny.traits._
import io.paradoxical.scala.tiny.{TinyTypeDefinition, TypeGroup}


case class ParsedTinyType(caseClass: String, fromTiny: String, toTiny: String)

object CaseClassMaker {
  def process(d: TinyTypeDefinition): ParsedTinyType = {
    d match {
      case TinyTypeDefinition(tinyName, typeName) =>

        val definition = s"case class $tinyName(data : $typeName) extends AnyVal"

        val tinyTitleName = tinyName(0).toUpper + tinyName.drop(1)

        val fromTiny =
          s"""
             |implicit def unbox${tinyTitleName}(i : $tinyName) : $typeName = i.data
             |implicit def unbox${tinyTitleName}(i : Option[$tinyName]) : Option[$typeName] = i.map(_.data)""".stripMargin

        val toTiny =
          s"""
             |implicit def box${tinyTitleName}(i : $typeName) : $tinyName = $tinyName(i)
             |implicit def box${tinyTitleName}(i : Option[$typeName]) : Option[$tinyName] = i.map($tinyName(_))""".stripMargin

        ParsedTinyType(definition, fromTiny, toTiny)
    }
  }
}

abstract class CaseClassMaker(config: TypeGroup) extends TinyMaker with OutputProvider {

  def getWriter(): Writer = {
    val parsed: Seq[ParsedTinyType] = config.types.map(CaseClassMaker.process)

    val tinyTemplates = parsed.map(_.caseClass).mkString(System.lineSeparator())

    val tinyConversions =
      withConversionsFromTiny(parsed.map(_.fromTiny).mkString(System.lineSeparator()))

    val toTinyConversion =
      withConversionsToTinyType(parsed.map(_.toTiny).mkString(System.lineSeparator()))

    new Writer {
      override def write(): Unit = {
        val writer = getOutputProvider()

        writer.write(
          s"""
$tinyTemplates

$tinyConversions

$toTinyConversion
          """.trim, s"${config.className}.scala")
      }
    }
  }

  def withConversionsFromTiny(content: String) = {
    s"""
object ConversionUnbox{
${content.split(System.lineSeparator()).map(i => "    " + i.trim).mkString(System.lineSeparator())}
}""".trim
  }

  def withConversionsToTinyType(content: String) = {
    s"""
object ConversionBox{
${content.split(System.lineSeparator()).map(i => "    " + i.trim).mkString(System.lineSeparator())}
}""".trim
  }
}
