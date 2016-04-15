import com.devshorts.data.bar
import org.scalatest.{FlatSpec, Matchers}

/**
  * Tests only for compilation
  */
class UseTypes {

  import com.devshorts.data.typetag2.TinyType._

  val funId: funId = FunId(1)
  val barId: barId = BarId(1)

  acceptsAlias(FunId(1))
//  acceptsAlias(barId) [ fails because barId is not a fooId even though they are both int ]
  acceptsAlias(funId)

  def acceptsAlias(funId: funId) = funId
}

class TestTypes extends FlatSpec with Matchers {
  "A tiny type" should "be convertable" in {
    import com.devshorts.data.Conversions._

    val tinyBar: bar = bar("foo")

    def takesString(s: String): String = s

    takesString(tinyBar) should be(tinyBar.data)
  }

  "A tiny type" should "be assignable" in {
    import com.devshorts.data.Conversions._

    val tinyBar = bar("foo")

    val s: String = tinyBar

    s should be("foo")
  }
}
