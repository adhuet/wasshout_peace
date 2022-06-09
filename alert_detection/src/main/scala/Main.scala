import org.apache.spark.sql.catalyst.dsl.expressions.StringToAttributeConversionHelper
import org.apache.spark.sql.functions.{col, concat, concat_ws, from_json, lit}
import org.apache.spark.sql.{ForeachWriter, Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object Main {
  def main(args: Array[String]): Unit = {
    // Spark session
    val spark = SparkSession
      .builder
      .appName("Spark-Kafka-Integration")
      .master("local")
      .getOrCreate()

    // Schema for the reports in Json format
    val schema = StructType(List(
      StructField("name", StringType, true),
      StructField("score", IntegerType, true)
    ))

    // Initial DF
    val init_df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "reports")
      .load()

    // Json conversion
    val clean_df = init_df
      .withColumn("value",from_json(col("value").cast(StringType), schema))
      .select(col("value.*"))

    // Keep only rows that have a score less than 20
    val alert_df = clean_df.where(col("score") < 20)

    // Convert DF to kafka stream format
    val kafka_df = alert_df.withColumn(
      "value",
      concat_ws("|", col("name"), col("score"))
    )

    // Write to Kafka sink
    kafka_df
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "alerts")
      .option("checkpointLocation", "checkpoint/kafka_checkpoint")
      .start()
      .awaitTermination()

//    //Write to console sink
//    alert_df.writeStream
//      .format("console")
//      .option("truncate","false")
//      //      .foreach(customWriter)
//      .start()
//      .awaitTermination()
  }
}