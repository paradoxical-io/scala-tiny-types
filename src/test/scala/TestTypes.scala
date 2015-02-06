import com.devshorts.makers.ScalaMaker
import com.devshorts.traits.Output
import com.devshorts.Config
import com.devshorts.data.bar
import org.scalatest.{FlatSpec, Matchers}
import com.devshorts.parsers.Implicits._
import scala.collection.mutable

class TestTypes extends FlatSpec with Matchers {
  "A tiny type" should "be convertable" in {
    import com.devshorts.data.Conversions._

    val tinyBar = bar("foo")

    def takesString(s: String): String = s

    takesString(tinyBar) should be(tinyBar.data)
  }

  "A tiny type" should "be assignable" in {
    import com.devshorts.data.Conversions._

    val tinyBar = bar("foo")

    val s: String = tinyBar

    s should be("foo")
  }

  "com.devshorts.Definer" should "split" in {

    val t = "foo, Bar".toTinyType

    t.tinyName should be ("foo")
    t.typeName should be ("Bar")
  }

  "Reading" should "read" in {
    val config = Config(definitionsFile = "data.tinyTypes", outputPackage = "com.devshorts.data")

    val _content = new mutable.ArrayBuffer[String]

    val tinyDef = "foo, Bar"

    val scalaMaker = new ScalaMaker(config, Seq(tinyDef.toTinyType)) {
      override def getOutputProvider(): Output = new Output {
        override def write(content: String, fileName: String): Unit = {
          _content += content
        }
      }
    }

    scalaMaker make() write()

    _content(0) should equal (ScalaMaker.process(tinyDef.toTinyType)._1)
    _content(1).contains("implicit def convertfoo(i : foo) : Bar = i.data") should be (true)
  }
}
