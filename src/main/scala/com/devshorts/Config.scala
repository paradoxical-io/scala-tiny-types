package com.devshorts

import java.io.File

case class Config(definitionsFile: String = null,
                  outputPackage: String = null,
                  rootFolder: String = new File(".").getCanonicalPath + "/src/main/scala/")
