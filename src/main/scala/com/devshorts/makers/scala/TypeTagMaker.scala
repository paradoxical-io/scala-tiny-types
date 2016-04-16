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

  private def toFirstUpper(s: String) = s.charAt(0).toString.toUpperCase() + s.substring(1, s.length)

  private def getFileHeader(config: TypeGroup): String = {
    config.fileHeader match {
      case Some(template) =>
        template.
          replace("${fileName}", config.className).
          replace("${year}", new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime))
      case None => ""
    }
  }

  override def getWriter(): Writer = new Writer {
    override def write(): Unit = {
      val provider: Output = new ScalaPackageWriter {
        override val folder: String = config.folder
        override val packageName: String = config.packageName
        override val fileHeader: String = getFileHeader(config)
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
  }
}
