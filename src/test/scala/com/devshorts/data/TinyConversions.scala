package com.devshorts.data

object Conversions{
    implicit def convertfoo(i : foo) : String = i.data
    implicit def convertbar(i : bar) : String = i.data
    implicit def convertbizBaz(i : bizBaz) : Int = i.data
    implicit def convertData(i : Data) : java.util.UUID = i.data
}