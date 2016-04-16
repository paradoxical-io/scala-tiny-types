package com.devshorts

import java.io.InputStream

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object TypeAliasType extends Enumeration {
  val CaseClass, TypeTag = Value
}

case class TinyTypeDefinition(tinyName: String, typeName: String)

case class Config(typeGroups: Seq[TypeGroup])

case class TypeGroup(
  packageName: String,
  className: String = "TinyTypes",
  creationType: TypeAliasType.Value = TypeAliasType.CaseClass,
  folder: String,
  types: Seq[TinyTypeDefinition],
  fileHeader: Option[String]
)

case class RawConfig(
  @JsonProperty("package") packageName: String,
  className: String = "TinyTypes",
  @JsonProperty("format") creationType: String,
  folder: String,
  tiny: Map[String, String],
  fileHeader: Option[String]
)

object Config {
  def apply(file: InputStream): Config = {
    val module: ObjectMapper =
      new ObjectMapper().registerModule(DefaultScalaModule)

    module.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)

    val c: List[RawConfig] = module.readValue(file, new TypeReference[List[RawConfig]] {})

    val typeGroups: List[TypeGroup] =
      c.map(x =>
        new TypeGroup(
          packageName = x.packageName,
          className = if (x.className == null) "TinyType" else x.className,
          creationType = if (x.creationType == null) TypeAliasType.CaseClass else TypeAliasType.withName(x.creationType),
          folder = x.folder,
          types = x.tiny.map(parseTypeDefinitions) toList,
          fileHeader = x.fileHeader
        )
      )

    Config(typeGroups)
  }

  private def parseTypeDefinitions(entry: (String, String)): TinyTypeDefinition =
    entry match {
      case (key, value) => new TinyTypeDefinition(key, value)
    }
}
