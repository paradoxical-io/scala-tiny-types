package io.paradoxical.scala.tiny

import io.paradoxical.scala.tiny.providers.MakerProvider
import io.paradoxical.scala.tiny.traits.Input

abstract class Runner(config: Seq[TypeGroup]) extends Input {

  def run(): Unit = {

    config.foreach(MakerProvider(_) getWriter() write())

    println("DONE!")
  }
}
