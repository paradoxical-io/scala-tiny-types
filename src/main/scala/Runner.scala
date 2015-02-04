import java.io.{FileWriter, BufferedWriter, File}

import scala.io._

class Writer(root : String){
  def writeAsPackage(packageName: String, content: String, fileName: String): Unit = {
    val dir = s"$root/${packageName.replace('.', '/')}"

    val f = new File(s"$dir/$fileName")

    if (!f.getParentFile.exists()) {
      f.getParentFile.mkdirs()
    }

    val writer = new BufferedWriter(new FileWriter(f))

    println(s"Writing to ${f.getAbsolutePath}")

    writer.write(s"""package $packageName

$content""")

    writer.close()
  }
}

class Runner(c : Config) {
  def run(): Unit = {

    val tinyTypeDefFile = c.definitionsFile

    val outputPackage = c.outputPackage

    val outputFolder = c.rootFolder match {
      case Some(r) => r
      case None => new File(".").getCanonicalPath
    }

    val (templatedTypes, templatedConversions) =
      readFile(tinyTypeDefFile)
        .map(x => { println(s"Processing $x"); x })
        .map(TinyMaker(_))
        .map({ case TinyAggregate(declaration, conversion) => (declaration, conversion)})
        .unzip

    val tinyTemplates = templatedTypes.mkString(System.lineSeparator())

    val tinyConversions = withObjectConversions(templatedConversions.mkString(System.lineSeparator()))

    val writer = new Writer(outputFolder)

    writer.writeAsPackage(outputPackage, tinyTemplates, "TinyTypes.scala")
    writer.writeAsPackage(outputPackage, tinyConversions, "TinyConversions.scala")

    println("DONE!")
  }

  def readFile(path: String): Seq[TinyTypeDefinition] = {
    val s = Source.fromFile(path)(Codec.UTF8)

    s.getLines().map(x => TinyTypeDefinition(x)).toSeq
  }

  def withObjectConversions(content: String) = {
    s"""
object Conversions{
${content.split(System.lineSeparator()).map(i => "    " + i.trim).mkString(System.lineSeparator())}
}""".trim
  }

}
