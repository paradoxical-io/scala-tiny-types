package com.devshorts.makers

import java.io.{BufferedWriter, File, FileWriter}

import com.devshorts._
import com.devshorts.parsers.TinyTypeDefinition
import com.devshorts.traits._


trait ScalaLiveWriter extends Output {
  val root: String

  val packageName: String

  def write(content: String, fileName: String): Unit = {
    val dir = s"$root/${packageName.replace('.', '/')}"

    val f = new File(s"$dir/$fileName")

    if (!f.getParentFile.exists()) {
      f.getParentFile.mkdirs()
    }

    val writer = new BufferedWriter(new FileWriter(f))

    println(s"Writing to ${f.getAbsolutePath}")

    writer.write( s"""package $packageName

$content""")

    writer.close()
  }
}

object ScalaMaker {
  def process(d: TinyTypeDefinition): (String, String) = {
    d match {
      case TinyTypeDefinition(tinyName, typeName) =>

        val definition = s"case class $tinyName(data : $typeName)"
        val conversion = s"implicit def convert${tinyName}(i : $tinyName) : $typeName = i.data"

        (definition, conversion)
    }
  }
}

abstract class ScalaMaker(c: Config, definitions: Seq[TinyTypeDefinition]) extends TinyMaker with OutputProvider {

  def make(): Writer = {
    val (templatedTypes, templatedConversions) = definitions.map(ScalaMaker.process).unzip

    val tinyTemplates = templatedTypes.mkString(System.lineSeparator())

    val tinyConversions = withObjectConversions(templatedConversions.mkString(System.lineSeparator()))

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
