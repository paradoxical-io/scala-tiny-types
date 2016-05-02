package io.paradoxical.scala.tiny.makers.scala

import io.paradoxical.scala.tiny._
import io.paradoxical.scala.tiny.{TinyTypeDefinition, TypeGroup}
import io.paradoxical.scala.tiny.traits._


case class ParsedTinyType(caseClass: String, conversion: String)

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

abstract class CaseClassMaker(config: TypeGroup) extends TinyMaker with OutputProvider {

  def getWriter(): Writer = {
    val parsed: Seq[ParsedTinyType] = config.types.map(CaseClassMaker.process)

    val tinyTemplates = parsed.map(_.caseClass).mkString(System.lineSeparator())

    val tinyConversions = withObjectConversions(parsed.map(_.conversion).mkString(System.lineSeparator()))

    new Writer {
      override def write(): Unit = {
        val writer = getOutputProvider()

        writer.write(s"""
$tinyTemplates

$tinyConversions
          """.trim, s"${config.className}.scala")
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
