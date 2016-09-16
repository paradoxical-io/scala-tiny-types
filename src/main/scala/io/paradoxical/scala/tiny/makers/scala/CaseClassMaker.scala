package io.paradoxical.scala.tiny.makers.scala

import io.paradoxical.scala.tiny.traits._
import io.paradoxical.scala.tiny.{Templator, TinyTypeDefinition, TypeGroup}


case class ParsedTinyType(caseClass: String, fromTiny: String, toTiny: String, imports: Seq[String] = Seq())

object CaseClassMaker {
  def process(d: TinyTypeDefinition): ParsedTinyType = {
    d match {
      case TinyTypeDefinition(tinyName, typeName, extractionName, canBeAnyVal, genJackson) =>

        val fieldAnnotations = if (genJackson) {
          "@(JsonValue @getter) "
        } else {
          ""
        }

        val classAnnotations = if (genJackson) {
          " @JsonCreator(mode=JsonCreator.Mode.DELEGATING)"
        } else {
          ""
        }
        val definition = s"case class ${tinyName}${classAnnotations}(${fieldAnnotations}${extractionName}: $typeName) " + (if (canBeAnyVal) "extends AnyVal" else "")

        val tinyTitleName = tinyName(0).toUpper + tinyName.drop(1)

        val canBuildToTiny = Templator("CanBuildToTiny.vm", Map(
          "SourceType" -> typeName,
          "TinyTypeTileName" -> tinyTitleName,
          "TinyType" -> tinyName
        ))

        val canBuildToValueType = Templator("CanBuildFromTiny.vm", Map(
          "SourceType" -> typeName,
          "TinyTypeTileName" -> tinyTitleName,
          "TinyType" -> tinyName,
          "extractor" -> extractionName
        ))

        val fromTiny =
          s"""
      ${canBuildToValueType}
             |implicit def unbox${tinyTitleName}(i : $tinyName) : $typeName = i.${extractionName}
             |implicit def unbox${tinyTitleName}(i : Option[$tinyName]) : Option[$typeName] = i.map(_.${extractionName})""".stripMargin

        val toTiny =
          s"""
      ${canBuildToTiny}
             |implicit def box${tinyTitleName}(i : $typeName) : $tinyName = $tinyName(i)
             |implicit def box${tinyTitleName}(i : Option[$typeName]) : Option[$tinyName] = i.map($tinyName(_))""".stripMargin

        ParsedTinyType(definition, fromTiny, toTiny, imports = if (genJackson) {
          Seq("import com.fasterxml.jackson.annotation.{JsonCreator, JsonValue}",
            "import scala.annotation.meta.{getter}",
            "import scala.collection.generic.CanBuildFrom",
            "import scala.collection.mutable"
          )
        } else {
          Seq("import scala.collection.generic.CanBuildFrom", "import scala.collection.mutable")
        })
    }
  }
}

abstract class CaseClassMaker(config: TypeGroup) extends TinyMaker with OutputProvider {

  def getWriter(): Writer = {
    val parsed: Seq[ParsedTinyType] = config.types.map(CaseClassMaker.process)

    val imports = parsed.flatMap(_.imports).distinct.mkString(System.lineSeparator())

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
$imports
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
