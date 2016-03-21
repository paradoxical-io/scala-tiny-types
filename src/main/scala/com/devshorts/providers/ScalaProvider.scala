package com.devshorts.providers

import com.devshorts.makers.scala.{CaseClassMaker, TypeTagMaker}
import com.devshorts.traits.{Output, ScalaPackageWriter, TinyMaker, TinyProvider}
import com.devshorts.{Config, TypeAliasType, TypeGroup}

class ScalaProvider(config: TypeGroup) extends TinyProvider {

  def tinyMaker: TinyMaker = {
    val packageWriter = new ScalaPackageWriter {
      override val folder     : String = config.folder
      override val packageName: String = config.packageName
    }

    config.creationType match {
      case TypeAliasType.CaseClass =>
        new CaseClassMaker(config) {
          override def getOutputProvider(): Output = packageWriter
        }
      case TypeAliasType.TypeTag   =>
        new TypeTagMaker(config) {
          override def getOutputProvider(): Output = packageWriter
        }
    }
  }
}

object MakerProvider {
  def apply(config : TypeGroup) : TinyMaker = {
    new ScalaProvider(config).tinyMaker
  }
}