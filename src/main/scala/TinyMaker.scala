
case class TinyAggregate(definition : String, conversion: String)

object TinyMaker {
  def apply(t : TinyTypeDefinition) : TinyAggregate = {
    t match {
      case TinyTypeDefinition(tinyName, typeName) =>

        val definition = s"case class $tinyName(data : $typeName)"
        val conversion = s"implicit def convert${tinyName}(i : $tinyName) : $typeName = i match { case $tinyName(data) => data }"

        TinyAggregate(definition, conversion)
    }
  }
}
