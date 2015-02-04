
case class TinyTypeDefinition(tinyName : String, typeName : String)

object TinyTypeDefinition{
  def apply(s : String) : TinyTypeDefinition = {
    val items = s.split(",")

    TinyTypeDefinition(items(0).trim, items(1).trim)
  }
}