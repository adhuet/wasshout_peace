import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

object Main {

  def main(args: Array[String]): Unit = {
//    val conf = new Configuration()
//    conf.set("fs.defaultFS", "hdfs://localhost:9000")
//    val fs = FileSystem.get(conf)
//    val files = fs.listStatus(new Path("/reports"))

//    files.foreach(f => println(f.getPath.toString))

//    val reader = fs.open(new Path("/reports/6-2_10-6-2022.csv"))
//    println(reader.readUTF())
//    println(reader.readUTF())

    val spark = SparkSession
      .builder
      .appName("report-analysis")
      .master("local")
      .getOrCreate()

    val schema = new StructType()
      .add("name",StringType,true)
      .add("score",IntegerType,true)

    val df:DataFrame = spark.read
//      .option("header", "false")
      .csv("hdfs://localhost:9000/reports/6-2_10-6-2022.csv")
//      .csv("test_csv")

//    df.printSchema()
    df.show(2)
  }



}
