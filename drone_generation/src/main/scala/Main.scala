import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      println("Invalid input, expected: jsonFile bootstrapServer topic")
      System.exit(1)
    }

    // Config variables
    val reportsFile = args(0)
    val bootstrapServer = args(1)
    val topic = args(2)

    // Reports from file
    val bufferedSource = Source.fromFile(reportsFile)
    val reportJsonStrings = bufferedSource.getLines().toList


    reportJsonStrings.foreach(s => println(s))
    // Kafka Producer
    val producer = ReportProducer(bootstrapServer, topic)

    // Send reports indefinitely
    try {
      handle_reports(producer, reportJsonStrings)
    }
    finally {
      bufferedSource.close()
    }
  }

  def handle_reports(producer: ReportProducer, reportJsonStrings: List[String]):Unit = {
    reportJsonStrings.foreach(s => producer.produceMessage(s))
    println("send batch")

    Thread.sleep(4000)
    handle_reports(producer, reportJsonStrings)
  }
}