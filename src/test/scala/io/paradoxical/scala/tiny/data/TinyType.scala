
package io.paradoxical.scala.tiny.data

case class bar(data : String) extends AnyVal
case class foo(data : String) extends AnyVal
case class bizBaz(data : Int) extends AnyVal
case class Data(data : java.util.UUID) extends AnyVal

object Conversions{
    
    implicit def convertbar(i : bar) : String = i.data
    implicit def convertOptionbar(i : Option[bar]) : Option[String] = i.map(_.data)
    
    implicit def convertfoo(i : foo) : String = i.data
    implicit def convertOptionfoo(i : Option[foo]) : Option[String] = i.map(_.data)
    
    implicit def convertbizBaz(i : bizBaz) : Int = i.data
    implicit def convertOptionbizBaz(i : Option[bizBaz]) : Option[Int] = i.map(_.data)
    
    implicit def convertData(i : Data) : java.util.UUID = i.data
    implicit def convertOptionData(i : Option[Data]) : Option[java.util.UUID] = i.map(_.data)
}