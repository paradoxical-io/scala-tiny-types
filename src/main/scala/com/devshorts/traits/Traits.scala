package com.devshorts.traits

import java.io.{BufferedWriter, File, FileWriter}

import scala.io.{Codec, Source}

trait Input {
  def readDefinitionsFile(path: String): Seq[String]
}

trait WriteProvider {
  def get(): Writer
}

trait TinyMaker {
  def getWriter(): Writer

  def complete() = {
    getWriter() write()
  }
}

trait TinyProvider {
  def tinyMaker : TinyMaker
}

trait LiveReader extends Input {
  override def readDefinitionsFile(path: String): Seq[String] = Source.fromFile(path)(Codec.UTF8).getLines().toSeq
}

trait Output {
  def write(content: String, fileName: String): Unit
}

trait OutputProvider {
  def getOutputProvider(): Output
}

trait Writer {
  def write(): Unit
}


