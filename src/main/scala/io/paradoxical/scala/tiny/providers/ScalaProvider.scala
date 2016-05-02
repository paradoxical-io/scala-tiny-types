package io.paradoxical.scala.tiny.providers

import io.paradoxical.scala.tiny.makers.scala.CaseClassMaker
import io.paradoxical.scala.tiny.traits.{Output, ScalaPackageWriter, TinyMaker, TinyProvider}
import io.paradoxical.scala.tiny.TypeGroup
import io.paradoxical.scala.tiny.{TypeAliasType, TypeGroup}
import io.paradoxical.scala.tiny.makers.scala.TypeTagMaker

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