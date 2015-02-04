# scala-tiny-types

This is a small console app to create scala based "tiny types". All this means is it'll auto generate case classes
and a "Conversions" object with a bunch of implicit functions to "dereference" the item.

Why tiny types? This way you pay no penalty in adding more domain specific information to your project. For example,
a first name isn't the same as a last name. And your address isn't the same as your age, so why would you represent them the same?

To use compile with sbt using 

```
> sbt
> compile
> assembly
```

Then give it the path to the defintions file (which is just a comma sepearated case class name followed by target type (Fully qualified)), 
and the output package type of the form "com.foo.bar".  It will write two files into "src/main/scala/com/foo/bar" being the case class tiny 
definitions as well as the implicit conversions.

## Example

As an example, given the following input file

```
foo, String
bar, String
bizBaz, Int
Data, java.util.UUID
```

And using the package `com.devshorts.data` we'll get 

```scala
package com.devshorts.data

case class foo(data : String)
case class bar(data : String)
case class bizBaz(data : Int)
case class Data(data : java.util.UUID)
```

```scala
package com.devshorts.data

object Conversions{
    implicit def convertfoo(i : foo) : String = i match { case foo(data) => data }
    implicit def convertbar(i : bar) : String = i match { case bar(data) => data }
    implicit def convertbizBaz(i : bizBaz) : Int = i match { case bizBaz(data) => data }
    implicit def convertData(i : Data) : java.util.UUID = i match { case Data(data) => data }
}
```


