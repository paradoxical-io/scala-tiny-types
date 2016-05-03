
package io.paradoxical.scala.tiny.data

case class bar(data : String) extends AnyVal
case class foo(data : String) extends AnyVal
case class bizBaz(data : Int) extends AnyVal
case class Data(data : java.util.UUID) extends AnyVal

object ConversionBox{
    
    implicit def unboxBar(i : bar) : String = i.data
    implicit def unboxBar(i : Option[bar]) : Option[String] = i.map(_.data)
    
    implicit def unboxFoo(i : foo) : String = i.data
    implicit def unboxFoo(i : Option[foo]) : Option[String] = i.map(_.data)
    
    implicit def unboxBizBaz(i : bizBaz) : Int = i.data
    implicit def unboxBizBaz(i : Option[bizBaz]) : Option[Int] = i.map(_.data)
    
    implicit def unboxData(i : Data) : java.util.UUID = i.data
    implicit def unboxData(i : Option[Data]) : Option[java.util.UUID] = i.map(_.data)
}

object ConversionUnbox{
    
    implicit def boxBar(i : String) : bar = bar(i)
    implicit def boxBar(i : Option[String]) : Option[bar] = i.map(bar(_))
    
    implicit def boxFoo(i : String) : foo = foo(i)
    implicit def boxFoo(i : Option[String]) : Option[foo] = i.map(foo(_))
    
    implicit def boxBizBaz(i : Int) : bizBaz = bizBaz(i)
    implicit def boxBizBaz(i : Option[Int]) : Option[bizBaz] = i.map(bizBaz(_))
    
    implicit def boxData(i : java.util.UUID) : Data = Data(i)
    implicit def boxData(i : Option[java.util.UUID]) : Option[Data] = i.map(Data(_))
}