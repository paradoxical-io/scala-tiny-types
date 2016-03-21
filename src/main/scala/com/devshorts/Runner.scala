package com.devshorts

import com.devshorts.providers.MakerProvider
import com.devshorts.traits.Input

abstract class Runner(config: Seq[TypeGroup]) extends Input {

  def run(): Unit = {

    config.foreach(MakerProvider(_) getWriter() write())

    println("DONE!")
  }
}
