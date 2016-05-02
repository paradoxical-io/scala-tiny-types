import io.paradoxical.{Conversions, bar}
import io.paradoxical.scala.tiny.data.typetag2.TinyType._
import org.scalatest.{FlatSpec, Matchers}

case class TestCase(data: funId)

/**
  * Tests only for compilation
  */
class UseTypes {

  val funId: funId = FunId(1)
  val barId: barId = BarId(1)

  val longId:Long = LongId(1)

  acceptsAlias(FunId(1))
//  acceptsAlias(barId) [ fails because barId is not a fooId even though they are both int ]
  acceptsAlias(funId)

  def acceptsAlias(funId: funId) = funId
}

class TestTypes extends FlatSpec with Matchers {
  "A tiny type" should "be convertable" in {
    import Conversions._

    val tinyBar: bar = bar("foo")

    def takesString(s: String): String = s

    takesString(tinyBar) should be(tinyBar.data)
  }

  "A tiny type" should "be assignable" in {
    import Conversions._

    val tinyBar = bar("foo")

    val s: String = tinyBar

    s should be("foo")
  }
}
