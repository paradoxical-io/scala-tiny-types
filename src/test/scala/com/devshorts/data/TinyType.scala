package com.devshorts.data

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