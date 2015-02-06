package com.devshorts

import com.devshorts.traits.Input
import com.devshorts.parsers.{TinyTypeDefinition, TinyTypeParser}

abstract class Runner(c: Config) extends Input {

  def run(): Unit = {

    val tinyTypeDefFile = c.definitionsFile

    val definitions = readFile(tinyTypeDefFile)
      .map(x => {
      println(s"Processing $x"); x
    })

    Definer(definitions) asScala(c) make() write()

    println("DONE!")
  }

  def readFile(path: String): Seq[TinyTypeDefinition] = readDefinitionsFile(path).map(TinyTypeParser(_)).toSeq
}
