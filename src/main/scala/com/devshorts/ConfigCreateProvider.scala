package com.devshorts

import com.devshorts.makers.{CaseClassMaker, TypeTagMaker}
import com.devshorts.parsers.TinyTypeDefinition
import com.devshorts.traits.{Output, ScalaPackageWriter, TinyMaker}

class ConfigCreateProvider(definitions: Seq[TinyTypeDefinition]) {

  def asScala(c: Config): TinyMaker = {
    val packageWriter = new ScalaPackageWriter {
      override val folder     : String = c.rootFolder
      override val packageName: String = c.outputPackage
    }

    c.creationType match {
      case TypeAliasType.CaseClass =>
        new CaseClassMaker(c, definitions) {
          override def getOutputProvider(): Output = packageWriter
        }
      case TypeAliasType.TypeTag   =>
        new TypeTagMaker(c, definitions) {
          override def getOutputProvider(): Output = packageWriter
        }
    }
  }
}

object ConfigCreateProvider {
  def apply(definitions: Seq[TinyTypeDefinition]) = new ConfigCreateProvider(definitions)
}