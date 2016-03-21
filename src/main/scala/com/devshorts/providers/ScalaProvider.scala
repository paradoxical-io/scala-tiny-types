package com.devshorts.providers

import com.devshorts.makers.scala.{CaseClassMaker, TypeTagMaker}
import com.devshorts.parsers.TinyTypeDefinition
import com.devshorts.traits.{Output, ScalaPackageWriter, TinyMaker, TinyProvider}
import com.devshorts.{Config, TypeAliasType}

class ScalaProvider(definitions: Seq[TinyTypeDefinition], config: Config) extends TinyProvider {

  def tinyMaker: TinyMaker = {
    val packageWriter = new ScalaPackageWriter {
      override val folder     : String = config.rootFolder
      override val packageName: String = config.outputPackage
    }

    config.creationType match {
      case TypeAliasType.CaseClass =>
        new CaseClassMaker(config, definitions) {
          override def getOutputProvider(): Output = packageWriter
        }
      case TypeAliasType.TypeTag   =>
        new TypeTagMaker(config, definitions) {
          override def getOutputProvider(): Output = packageWriter
        }
    }
  }
}

object MakerProvider {
  def apply(definitions: Seq[TinyTypeDefinition], config : Config) : TinyMaker = {
    new ScalaProvider(definitions, config).tinyMaker
  }
}