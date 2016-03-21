package com.devshorts

import java.io.File

object TypeAliasType extends Enumeration {
  val CaseClass, TypeTag = Value
}

case class Config(definitionsFile: String = null,
                  outputPackage: String = null,
                  rootFolder: String = new File(".").getCanonicalPath + "/src/main/scala/",
                  creationType : TypeAliasType.Value = TypeAliasType.CaseClass)
