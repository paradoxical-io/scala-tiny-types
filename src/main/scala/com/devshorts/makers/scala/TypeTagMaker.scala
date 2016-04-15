package com.devshorts.makers.scala

import com.devshorts.traits.{Output, OutputProvider, TinyMaker, Writer}
import com.devshorts.{TinyTypeDefinition, TypeGroup}

abstract class TypeTagMaker(config: TypeGroup) extends TinyMaker with OutputProvider {
  private def toTemplate(definition: TinyTypeDefinition): String = {
    s"""
  trait Tiny${toFirstUpper(definition.tinyName)}
  type ${definition.tinyName} = ${definition.typeName} with Tagged[Tiny${toFirstUpper(definition.tinyName)}]
  def ${toFirstUpper(definition.tinyName)}(rawType: ${definition.typeName}): ${definition.tinyName} = rawType.asInstanceOf[${definition.tinyName}]"""
  }

  private def toFirstUpper(s: String) = s.charAt(0).toString.toUpperCase() + s.substring(1, s.length)

  override def getWriter(): Writer = new Writer {
    override def write(): Unit = {
      val provider: Output = getOutputProvider()

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
