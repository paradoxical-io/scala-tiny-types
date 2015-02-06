package com.devshorts

import com.devshorts.makers.{ScalaLiveWriter, ScalaMaker}
import com.devshorts.traits.Output
import com.devshorts.parsers.TinyTypeDefinition

class Definer(definitions : Seq[TinyTypeDefinition]){

  def asScala(c: Config) : ScalaMaker = {
    new ScalaMaker(c, definitions) {
      override def getOutputProvider(): Output = new ScalaLiveWriter {
        override val root: String = c.rootFolder
        override val packageName: String = c.outputPackage
      }
    }
  }
}

object Definer{
  def apply(definitions: Seq[TinyTypeDefinition]) = new Definer(definitions)
}
