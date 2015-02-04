package com.devshorts.data

object Conversions{
    implicit def convertfoo(i : foo) : String = i match { case foo(data) => data }
    implicit def convertbar(i : bar) : String = i match { case bar(data) => data }
    implicit def convertbizBaz(i : bizBaz) : Int = i match { case bizBaz(data) => data }
    implicit def convertData(i : Data) : java.util.UUID = i match { case Data(data) => data }
}