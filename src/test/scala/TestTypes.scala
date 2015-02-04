import com.devshorts.data.bar
import org.scalatest.{Matchers, FlatSpec}

class TestTypes extends FlatSpec with Matchers{
  "A tiny type" should "be convertable" in {
    import com.devshorts.data.Conversions._

    val tinyBar = bar("foo")

    def takesString(s : String) : String = s

    takesString(tinyBar) should be (tinyBar.data)
  }
}
