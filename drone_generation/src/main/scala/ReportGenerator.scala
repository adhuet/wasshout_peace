import java.io.PrintStream
import scala.sys.process.{ProcessLogger, stderr, stdout}
import scala.sys.process._

class ReportGenerator {
  def generate(): Array[String] = {
    "python3 -m pip install faker" ! ProcessLogger(stdout append _)
    "python3 report_generator.py" ! ProcessLogger(stdout append _, stderr append _)
    stdout.toString().split('\n')
  }
}
