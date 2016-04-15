import com.devshorts.data.bar
import org.scalatest.{FlatSpec, Matchers}

/**
  * Tests only for compilation
  */
class UseTypes {

  import com.devshorts.data.typetag2.TinyType._

  val f: funId = FunId(1)
  val b: barId = BarId(1)

  acceptsAlias(FunId(1))
//  acceptsAlias(b) // should fail
  acceptsAlias(f)

  def acceptsAlias(p: funId) = p
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
