package com.devshorts.parsers

import com.devshorts.ConfigCreateProvider


case class TinyTypeDefinition(tinyName : String, typeName : String)

class StringExt(s : String) {
  def toTinyType = TinyTypeParser(s)
}

object Implicits {
  implicit def stringExt(s : String) : StringExt = new StringExt(s)
}

object TinyBuilder {
  def apply(s : Seq[String]) : ConfigCreateProvider = {
    import com.devshorts.parsers.Implicits._

    new ConfigCreateProvider(s.map(_.toTinyType))
  }
}

object TinyTypeParser{
  def apply(s : String) : TinyTypeDefinition = {
    val items = s.split(",")

    TinyTypeDefinition(items(0).trim, items(1).trim)
  }
}

