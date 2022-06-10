import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println("Invalid input, expected: bootstrapServer topic")
      System.exit(1)
    }

    // Config variables
    // val reportsFile = args(0)
    val bootstrapServer = args(0)
    val topic = args(1)

    // Reports from file
    // val bufferedSource = Source.fromFile(reportsFile)
    // val reportJsonStrings = bufferedSource.getLines().toList
    val generator : ReportGenerator = new ReportGenerator()

    // Kafka Producer
    val producer = ReportProducer(bootstrapServer, topic)

    // Send reports indefinitely
    try {
      handle_reports(producer, generator)
    }
    /* finally {
      bufferedSource.close()
    } */
  }

  def handle_reports(producer: ReportProducer, generator: ReportGenerator):Unit = {
    val reportJsonStrings = generator.generate()
    reportJsonStrings.foreach(s => producer.produceMessage(s))
    println("send batch")

    Thread.sleep(4000)
    handle_reports(producer, generator)
  }
}