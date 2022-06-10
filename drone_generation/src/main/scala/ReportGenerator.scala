import java.io.PrintStream
import scala.language.postfixOps
import scala.sys.process.{ProcessLogger, stderr, stdout}
import scala.sys.process._

class ReportGenerator {
  def generate(): Array[String] = {
    // "python3 -m pip install faker".!!
    val output = "python3 report_generator.py".!!
    output.trim.split("\n")
  }
}
