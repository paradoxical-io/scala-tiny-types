
package io.paradoxical.scala.tiny.data

case class bar(value: String)extends AnyVal
case class foo(value: String)extends AnyVal
case class bizBaz(value: Int)extends AnyVal
case class Data(value: java.util.UUID)extends AnyVal

object ConversionUnbox{
    
    implicit def unboxBar(i : bar) : String = i.value
    implicit def unboxBar(i : Option[bar]) : Option[String] = i.map(_.value)
    
    implicit def unboxFoo(i : foo) : String = i.value
    implicit def unboxFoo(i : Option[foo]) : Option[String] = i.map(_.value)
    
    implicit def unboxBizBaz(i : bizBaz) : Int = i.value
    implicit def unboxBizBaz(i : Option[bizBaz]) : Option[Int] = i.map(_.value)
    
    implicit def unboxData(i : Data) : java.util.UUID = i.value
    implicit def unboxData(i : Option[Data]) : Option[java.util.UUID] = i.map(_.value)
}

object ConversionBox{
    
    implicit def boxBar(i : String) : bar = bar(i)
    implicit def boxBar(i : Option[String]) : Option[bar] = i.map(bar(_))
    
    implicit def boxFoo(i : String) : foo = foo(i)
    implicit def boxFoo(i : Option[String]) : Option[foo] = i.map(foo(_))
    
    implicit def boxBizBaz(i : Int) : bizBaz = bizBaz(i)
    implicit def boxBizBaz(i : Option[Int]) : Option[bizBaz] = i.map(bizBaz(_))
    
    implicit def boxData(i : java.util.UUID) : Data = Data(i)
    implicit def boxData(i : Option[java.util.UUID]) : Option[Data] = i.map(Data(_))
}