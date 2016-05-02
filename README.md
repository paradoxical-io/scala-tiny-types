# scala-tiny-types

This is a small console app to create scala based "tiny types". 

Why tiny types? This way you pay no penalty in adding more domain specific information to your project. For example,
a first name isn't the same as a last name. And your address isn't the same as your age, so why would you represent them the same?

To install run the install script.

Run `tiny-types` and give it the path to the defintions file (which is just a comma sepearated case class name followed by target type (Fully qualified)),
and the output package type of the form "com.foo.bar".  It will write two files into "src/main/scala/com/foo/bar" being the case class tiny 
definitions as well as the implicit conversions.

## Usage

```
tiny-types 1.0
Usage: tiny-types [options]

  -d <value> | --definitionsFile <value>
        The json definitions file
```        

## TypeTagging vs Case Classes

There are two camps of tiny type aliasing, one is case class wrapping (which is the default) and the other is type tagging.

Type tags are inspired by this blog post: https://coderwall.com/p/l-plmq/adding-semantic-to-base-types-parameters-in-scala

## Example case classes

As an example, given the following input file

```
[
  {
    "package": "io.paradoxical.scala.tiny.data",
    "folder": "src/test/scala",
    "tiny": {
      "bar": "String",
      "foo": "String",
      "bizBaz": "Int",
      "Data" : "java.util.UUID"
    }
  }
]
```

We'll get 

```scala
package io.paradoxical.scala.tiny.data

case class bar(data : String) extends AnyVal
case class foo(data : String) extends AnyVal
case class bizBaz(data : Int) extends AnyVal
case class Data(data : java.util.UUID) extends AnyVal

object Conversions{
    implicit def convertbar(i : bar) : String = i.data
    implicit def convertfoo(i : foo) : String = i.data
    implicit def convertbizBaz(i : bizBaz) : Int = i.data
    implicit def convertData(i : Data) : java.util.UUID = i.data
}
```

# Example type tagging

With the following input file

```
[
  {
    "package": "io.paradoxical.scala.tiny.data.typetag2",
    "folder": "src/test/scala",
    "format": "TypeTag",
    "tiny": {
      "workId": "String",
      "funId": "Int",
      "barId": "Int",
      "tableId": "java.util.UUID"
    }
  }
]
```

We'll get

```scala
package io.paradoxical.scala.tiny.data.typetag2
object TinyType {
  type Tagged[U] = { type Tag = U }

  trait TinyWorkId
  type workId = String with Tagged[TinyWorkId]
  def WorkId(rawType: String): workId = rawType.asInstanceOf[workId]

  trait TinyFunId
  type funId = Int with Tagged[TinyFunId]
  def FunId(rawType: Int): funId = rawType.asInstanceOf[funId]

  trait TinyBarId
  type barId = Int with Tagged[TinyBarId]
  def BarId(rawType: Int): barId = rawType.asInstanceOf[barId]

  trait TinyTableId
  type tableId = java.util.UUID with Tagged[TinyTableId]
  def TableId(rawType: java.util.UUID): tableId = rawType.asInstanceOf[tableId]
}
```

Which can be used now:

```scala
import io.paradoxical.scala.tiny.data.typetag2.TinyType._

val funId: funId = FunId(1)
val barId: barId = BarId(1)

acceptsAlias(FunId(1))
//  acceptsAlias(barId) [ fails because barId is not a fooId even though they are both int ]
acceptsAlias(funId)

def acceptsAlias(funId: funId) = funId
```

## Zsh completion

```zsh
#compdef tiny-types

local arguments

arguments=(
'-d[Json tiny types descriptor]:descriptor file:_files'
)

_arguments -s $arguments
```

Drop this into your zsh functions folder as `_tiny-types` and you're good to go!


 
