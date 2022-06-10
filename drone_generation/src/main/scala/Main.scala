import scala.annotation.tailrec

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println("Invalid input, expected: bootstrapServer topic")
      System.exit(1)
    }

    // Config variables
    val bootstrapServer = args(0)
    val topic = args(1)

    // Reports from file
    val generator : ReportGenerator = new ReportGenerator()

    // Kafka Producer
    val producer = ReportProducer(bootstrapServer, topic)

    // Send reports indefinitely
    try {
      handle_reports(producer, generator)
    }
  }

  @tailrec
  def handle_reports(producer: ReportProducer, generator: ReportGenerator):Unit = {
    val reportJsonStrings = generator.generate()
    reportJsonStrings.foreach(s => producer.produceMessage(s))
    println("batch sent")

    Thread.sleep(4000)
    handle_reports(producer, generator)
  }
}