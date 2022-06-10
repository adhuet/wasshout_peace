import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, Path}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import net.liftweb.json._


import java.time.Duration
import java.util.{Calendar, Properties}
import java.util
import java.text.SimpleDateFormat
import scala.annotation.tailrec
import scala.collection.JavaConverters.iterableAsScalaIterableConverter

object Main {
  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "report_consumer_group")

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList("reports2"))

    val format = new SimpleDateFormat("m-h_d-M-y")
    val file_name = format.format(Calendar.getInstance().getTime)

    val conf = new Configuration()
    conf.set("fs.defaultFS", "hdfs://localhost:9000")
    val fs = FileSystem.get(conf)
    val output = fs.create(new Path(s"/reports/${file_name}.csv"))

    consumeReports(format, file_name, output, fs, consumer)
  }

  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats
  case class Report(name:String, score: Int)

  @tailrec
  def consumeReports(format: SimpleDateFormat, file_name: String, output: FSDataOutputStream, fs: FileSystem, consumer: KafkaConsumer[String, String]): Unit = {
    println(s"Consuming: report file{$file_name}")

    val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(4000))
    records.asScala.foreach { record =>
      println(record.value())
      val jValue = parse(record.value())
      val report = jValue.extract[Report]

      output.writeUTF(s"${report.name}, ${report.score}\n")
    }

    Thread.sleep(10000)
    val new_file_name = format.format(Calendar.getInstance().getTime)
    consumeReports(
      format,
      if (file_name == new_file_name) file_name else new_file_name,
      if (file_name == new_file_name) output else handleReportFile(output, fs, new_file_name),
      fs,
      consumer
    )
  }

  def handleReportFile(output: FSDataOutputStream, fs: FileSystem, new_file_name: String): FSDataOutputStream = {
    output.close()
    val new_output = fs.create(new Path(s"/reports/${new_file_name}.csv"))
    new_output
  }
}
