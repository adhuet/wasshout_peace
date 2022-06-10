import org.apache.spark.sql.functions.{col, concat_ws, from_json}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{FloatType, IntegerType, StringType, StructField, StructType, TimestampType}

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
      StructField("timestamp", TimestampType, nullable = true),
      StructField("id", IntegerType, nullable = true),
      StructField("name", StringType, nullable = true),
      StructField("score", IntegerType, nullable = true),
      StructField("latitude", FloatType, nullable = true),
      StructField("longitude", FloatType, nullable = true),
      StructField("words", StringType, nullable = true))
    )

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
      concat_ws("|",
        col("timestamp"),
        col("id"),
        col("name"),
        col("score"),
        col("latitude"),
        col("longitude"),
        col("words"))
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
  }
}
