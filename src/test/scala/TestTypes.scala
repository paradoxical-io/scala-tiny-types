import com.devshorts.Config
import com.devshorts.data.bar
import com.devshorts.makers.scala.CaseClassMaker
import com.devshorts.parsers.Implicits._
import com.devshorts.traits.Output
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable

/**
  * Tests only for compilation
  */
class UseTypes {
  import com.devshorts.data.typetag2.TinyTypes._

  val implicitConvertsToType: funId = 1

  def sendPrimitiveToTypeAlias = passPrimitiveToTypeAlias(1)
  def sendTypeAliasToTypeAlias = passPrimitiveToTypeAlias(implicitConvertsToType)

  def passPrimitiveToTypeAlias(p : funId) = p
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

  "com.devshorts.Definer" should "split" in {

    val t = "foo, Bar".toTinyType

    t.tinyName should be("foo")
    t.typeName should be("Bar")
  }

  "Reading" should "read" in {
    val config = Config(definitionsFile = "data.tinyTypes", outputPackage = "com.devshorts.data")

    val _content = new mutable.ArrayBuffer[String]

    val tinyDef = "foo, Bar"

    val scalaMaker = new CaseClassMaker(config, Seq(tinyDef.toTinyType)) {
      override def getOutputProvider(): Output = new Output {
        override def write(content: String, fileName: String): Unit = {
          _content += content
        }
      }
    }

    scalaMaker getWriter() write()

    _content(0) should equal(CaseClassMaker.process(tinyDef.toTinyType).caseClass)
    _content(1).contains("implicit def convertfoo(i : foo) : Bar = i.data") should be(true)
  }
}
