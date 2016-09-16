
package io.paradoxical.scala.tiny.data

import com.fasterxml.jackson.annotation.{JsonCreator, JsonValue}
import scala.annotation.meta.{getter}
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable

case class bar @JsonCreator(mode = JsonCreator.Mode.DELEGATING)(@(JsonValue@getter) value: String) extends AnyVal
case class foo @JsonCreator(mode = JsonCreator.Mode.DELEGATING)(@(JsonValue@getter) value: String) extends AnyVal
case class bizBaz @JsonCreator(mode = JsonCreator.Mode.DELEGATING)(@(JsonValue@getter) value: Int) extends AnyVal
case class Data @JsonCreator(mode = JsonCreator.Mode.DELEGATING)(@(JsonValue@getter) value: java.util.UUID) extends AnyVal

object ConversionUnbox {

  implicit val buildFromBar: CanBuildFrom[bar, bar, String] = new CanBuildFrom[bar, bar, String] {
    override def apply(from: bar): mutable.Builder[bar, String] = builder += from

    override def apply(): mutable.Builder[bar, String] = builder

    def builder: mutable.Builder[bar, String] = new mutable.Builder[bar, String] {
      var e: String = _

      override def += (elem: bar): this.type = {
        e = elem.value

        this
      }

      override def clear(): Unit = {}

      override def result(): String = e
    }
  }

  implicit def unboxBar(i: bar): String = i.value

  implicit def unboxBar(i: Option[bar]): Option[String] = i.map(_.value)

  implicit val buildFromFoo: CanBuildFrom[foo, foo, String] = new CanBuildFrom[foo, foo, String] {
    override def apply(from: foo): mutable.Builder[foo, String] = builder += from

    override def apply(): mutable.Builder[foo, String] = builder

    def builder: mutable.Builder[foo, String] = new mutable.Builder[foo, String] {
      var e: String = _

      override def += (elem: foo): this.type = {
        e = elem.value

        this
      }

      override def clear(): Unit = {}

      override def result(): String = e
    }
  }

  implicit def unboxFoo(i: foo): String = i.value

  implicit def unboxFoo(i: Option[foo]): Option[String] = i.map(_.value)

  implicit val buildFromBizBaz: CanBuildFrom[bizBaz, bizBaz, Int] = new CanBuildFrom[bizBaz, bizBaz, Int] {
    override def apply(from: bizBaz): mutable.Builder[bizBaz, Int] = builder += from

    override def apply(): mutable.Builder[bizBaz, Int] = builder

    def builder: mutable.Builder[bizBaz, Int] = new mutable.Builder[bizBaz, Int] {
      var e: Int = _

      override def += (elem: bizBaz): this.type = {
        e = elem.value

        this
      }

      override def clear(): Unit = {}

      override def result(): Int = e
    }
  }

  implicit def unboxBizBaz(i: bizBaz): Int = i.value

  implicit def unboxBizBaz(i: Option[bizBaz]): Option[Int] = i.map(_.value)

  implicit val buildFromData: CanBuildFrom[Data, Data, java.util.UUID] = new CanBuildFrom[Data, Data, java.util.UUID] {
    override def apply(from: Data): mutable.Builder[Data, java.util.UUID] = builder += from

    override def apply(): mutable.Builder[Data, java.util.UUID] = builder

    def builder: mutable.Builder[Data, java.util.UUID] = new mutable.Builder[Data, java.util.UUID] {
      var e: java.util.UUID = _

      override def += (elem: Data): this.type = {
        e = elem.value

        this
      }

      override def clear(): Unit = {}

      override def result(): java.util.UUID = e
    }
  }

  implicit def unboxData(i: Data): java.util.UUID = i.value

  implicit def unboxData(i: Option[Data]): Option[java.util.UUID] = i.map(_.value)
}

object ConversionBox {

  implicit val buildToBar: CanBuildFrom[String, String, bar] = new CanBuildFrom[String, String, bar] {
    override def apply(from: String): mutable.Builder[String, bar] = builder += from

    override def apply(): mutable.Builder[String, bar] = builder

    def builder: mutable.Builder[String, bar] = new mutable.Builder[String, bar] {
      var e: String = _

      override def += (elem: String): this.type = {
        e = elem

        this
      }

      override def clear(): Unit = {}

      override def result(): bar = bar(e)
    }
  }

  implicit def boxBar(i: String): bar = bar(i)

  implicit def boxBar(i: Option[String]): Option[bar] = i.map(bar(_))

  implicit val buildToFoo: CanBuildFrom[String, String, foo] = new CanBuildFrom[String, String, foo] {
    override def apply(from: String): mutable.Builder[String, foo] = builder += from

    override def apply(): mutable.Builder[String, foo] = builder

    def builder: mutable.Builder[String, foo] = new mutable.Builder[String, foo] {
      var e: String = _

      override def += (elem: String): this.type = {
        e = elem

        this
      }

      override def clear(): Unit = {}

      override def result(): foo = foo(e)
    }
  }

  implicit def boxFoo(i: String): foo = foo(i)

  implicit def boxFoo(i: Option[String]): Option[foo] = i.map(foo(_))

  implicit val buildToBizBaz: CanBuildFrom[Int, Int, bizBaz] = new CanBuildFrom[Int, Int, bizBaz] {
    override def apply(from: Int): mutable.Builder[Int, bizBaz] = builder += from

    override def apply(): mutable.Builder[Int, bizBaz] = builder

    def builder: mutable.Builder[Int, bizBaz] = new mutable.Builder[Int, bizBaz] {
      var e: Int = _

      override def += (elem: Int): this.type = {
        e = elem

        this
      }

      override def clear(): Unit = {}

      override def result(): bizBaz = bizBaz(e)
    }
  }

  implicit def boxBizBaz(i: Int): bizBaz = bizBaz(i)

  implicit def boxBizBaz(i: Option[Int]): Option[bizBaz] = i.map(bizBaz(_))

  implicit val buildToData: CanBuildFrom[java.util.UUID, java.util.UUID, Data] = new CanBuildFrom[java.util.UUID, java.util.UUID, Data] {
    override def apply(from: java.util.UUID): mutable.Builder[java.util.UUID, Data] = builder += from

    override def apply(): mutable.Builder[java.util.UUID, Data] = builder

    def builder: mutable.Builder[java.util.UUID, Data] = new mutable.Builder[java.util.UUID, Data] {
      var e: java.util.UUID = _

      override def += (elem: java.util.UUID): this.type = {
        e = elem

        this
      }

      override def clear(): Unit = {}

      override def result(): Data = Data(e)
    }
  }

  implicit def boxData(i: java.util.UUID): Data = Data(i)

  implicit def boxData(i: Option[java.util.UUID]): Option[Data] = i.map(Data(_))
}