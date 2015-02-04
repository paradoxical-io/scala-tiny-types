import java.io.{FileWriter, BufferedWriter, File}

import com.sun.tools.internal.ws.processor.util.DirectoryUtil

import scala.io.{Codec, Source}


object Main extends App {
  
  val tinyTypeDefFile = args(0)

  val outputPackage = args(1)

  println(s"using $tinyTypeDefFile, $outputPackage")
  
  var (templatedTypes, templatedConversions) =
    readFile(tinyTypeDefFile)
      .map(x => {println(s"Processing $x"); x})
      .map(x => TinyMaker(x))
      .map({ case TinyAggregate(declaration, conversion) => (declaration, conversion) })
      .unzip

  val tinyTemplates = templatedTypes.mkString(System.lineSeparator())

  val tinyConversions = withObjectConversions(templatedConversions.mkString(System.lineSeparator()))

  writeAsPackage(outputPackage, tinyTemplates, "TinyTypes.scala")
  writeAsPackage(outputPackage, tinyConversions, "TinyConversions.scala")

  def readFile(path: String): Seq[TinyTypeDefinition] = {
    val s = Source.fromFile(path)(Codec.UTF8)

    val r = s.getLines().map(x => TinyTypeDefinition(x)).toSeq

    //s.close()

    r
  }

  def writeAsPackage(packageName : String, content: String, fileName : String): Unit ={
    val dir = s"src/main/scala/${packageName.replace('.','/')}"

    val f = new File(s"$dir/$fileName")

    if(!f.getParentFile.exists()) {
      f.getParentFile.mkdirs()
    }

    val writer = new BufferedWriter(new FileWriter(f))

    writer.write(content)

    writer.close()
  }

  def withObjectConversions(content : String) = {
    s"""
object Conversions{
${content.split(System.lineSeparator()).map(i => "    " + i.trim).mkString(System.lineSeparator())}
}""".trim
  }
}
