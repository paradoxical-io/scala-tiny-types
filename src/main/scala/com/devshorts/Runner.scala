package com.devshorts

import com.devshorts.parsers.{TinyTypeDefinition, TinyTypeParser}
import com.devshorts.providers.MakerProvider
import com.devshorts.traits.Input

abstract class Runner(config: Config) extends Input {

  def run(): Unit = {

    val tinyTypeDefFile = config.definitionsFile

    val definitions = readFile(tinyTypeDefFile)
                      .map(x => {
                        println(s"Processing $x")
                        x
                      })

    MakerProvider(definitions, config) getWriter() write()

    println("DONE!")
  }

  def readFile(path: String): Seq[TinyTypeDefinition] = readDefinitionsFile(path).map(TinyTypeParser(_)).toSeq
}
