package com.devshorts.makers.scala

import com.devshorts.traits.{Output, OutputProvider, ScalaPackageWriter, TinyMaker, Writer}
import com.devshorts.{TinyTypeDefinition, TypeGroup}
import java.text.SimpleDateFormat
import java.util.Calendar

abstract class TypeTagMaker(config: TypeGroup) extends TinyMaker with OutputProvider {
  private def toTemplate(definition: TinyTypeDefinition): String = {
    s"""
  trait Tiny${toFirstUpper(definition.tinyName)}
  type ${definition.tinyName} = ${definition.typeName} with Tagged[Tiny${toFirstUpper(definition.tinyName)}]
  def ${toFirstUpper(definition.tinyName)}(rawType: ${definition.typeName}): ${definition.tinyName} = rawType.asInstanceOf[${definition.tinyName}]"""
  }

  private def toImplicits(definition: TinyTypeDefinition): String = {
    val mainImplicit =
      s"""
    implicit def convertTo${toFirstUpper(definition.tinyName)}(rawType: ${definition.typeName}): ${definition.tinyName} = rawType.asInstanceOf[${definition.tinyName}]"""

    definition.typeName match {
      case "Integer" =>
        s"""
    implicit def convertTo${toFirstUpper(definition.tinyName)}(rawType: Int): ${definition.tinyName} = rawType.asInstanceOf[${definition.tinyName}]""" +
        mainImplicit

      case _ =>
        mainImplicit
    }
  }

  private def toFirstUpper(s: String) = s.charAt(0).toString.toUpperCase() + s.substring(1, s.length)

  private def getFileHeader(config: TypeGroup, fileName: String): String = {
    config.fileHeader match {
      case Some(template) =>
        template.
          replace("${fileName}", fileName).
          replace("${year}", new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime))
      case None => ""
    }
  }

  override def getWriter(): Writer = new Writer {

    def writeBody(): Unit = {
      val provider: Output = new ScalaPackageWriter {
        override val folder: String = config.folder
        override val packageName: String = config.packageName
        override val fileHeader: String = getFileHeader(config, config.className)
      }

      val types: String = config.types.map(toTemplate).mkString(System.lineSeparator())

      val body =
        s"""
object ${config.className} {
  type Tagged[U] = { type Tag = U }
  $types
}
        """.trim

      provider.write(body, s"${config.className}.scala")
    }

    def writeImplicits() = {

      val className = s"${config.className}Implicits"
      val provider: Output = new ScalaPackageWriter {
        override val folder: String = config.folder
        override val packageName: String = config.packageName
        override val fileHeader: String = getFileHeader(config, className)
      }

      val types: String = config.types.map(toImplicits).mkString(System.lineSeparator())

      val body =
        s"""
import ${config.packageName}.${config.className}._
object ${className} {
  $types
}
        """.trim

      provider.write(body, s"${className}.scala")
    }

    override def write(): Unit = {
      writeBody()

      if (config.generateImplicits.isDefined) {
        writeImplicits()
      }
    }
  }
}
