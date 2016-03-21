package com.devshorts.makers.scala

import com.devshorts.Config
import com.devshorts.parsers.TinyTypeDefinition
import com.devshorts.traits.{Output, OutputProvider, TinyMaker, Writer}

abstract class TypeTagMaker(config: Config, definitions: Seq[TinyTypeDefinition]) extends TinyMaker with OutputProvider {
  private def toTemplate(definition: TinyTypeDefinition) : String = {
    s"""
  /* Tiny type definition for ${definition.tinyName} */

  trait Tiny${definition.tinyName}

  type ${definition.tinyName} = ${definition.typeName} @@ Tiny${definition.tinyName}

  implicit def ${camelCase(definition.tinyName)}(rawType : ${definition.typeName}) : ${definition.tinyName} = rawType.asInstanceOf[${definition.tinyName}]
""".stripMargin
  }

  private def camelCase(s : String) = s.charAt(0).toString.toLowerCase + s.substring(1, s.length)

  override def getWriter(): Writer = new Writer {
    override def write(): Unit = {
      val provider: Output = getOutputProvider()

      val types : String = definitions.map(toTemplate).mkString(System.lineSeparator()).trim

      val body =
        s"""
object ${config.className} {
  type Tagged[U] = { type Tag = U }
  type @@[T, U] = T with Tagged[U]

$types
}
        """.trim

      provider.write(body, s"${config.className}.scala")
    }
  }
}
