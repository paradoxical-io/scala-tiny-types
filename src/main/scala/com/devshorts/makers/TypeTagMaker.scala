package com.devshorts.makers

import com.devshorts.Config
import com.devshorts.parsers.TinyTypeDefinition
import com.devshorts.traits.{Output, OutputProvider, TinyMaker, Writer}

abstract class TypeTagMaker(config: Config, definitions: Seq[TinyTypeDefinition]) extends TinyMaker with OutputProvider {
  private def toTemplate(definition: TinyTypeDefinition) : String = {
    s"""
  /* Tiny type definition for ${definition.tinyName} */

  trait Tiny${definition.tinyName}
  type ${definition.tinyName} = ${definition.typeName} @@ Tiny${definition.tinyName}
  implicit def ${camelCase(definition.tinyName)}(rawType : ${definition.typeName}) : ${definition.tinyName} =
    rawType.asInstanceOf[${definition.tinyName}]

    """.stripMargin
  }

  private def camelCase(s : String) = s.charAt(0).toString.toLowerCase + s.substring(1, s.length)

  private def typeTagDefinition() : String = {
    """
object TagTypes {
    type Tagged[U] = { type Tag = U }
    type @@[T, U] = T with Tagged[U]
}
    """.stripMargin
  }
  override def make(): Writer = new Writer {
    override def write(): Unit = {
      val provider: Output = getOutputProvider()

      val types : String = definitions.map(toTemplate).mkString(System.lineSeparator())

      val body =
        s"""
import TagTypes.@@

object TinyTypes {
   $types
}

${typeTagDefinition()}
        """.stripMargin

      provider.write(body, "TinyTypes.scala")
    }
  }
}
