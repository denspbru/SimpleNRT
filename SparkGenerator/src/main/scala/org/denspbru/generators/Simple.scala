package org.denspbru.generators


import com.typesafe.config.ConfigFactory
import org.apache.log4j.LogManager
import org.apache.spark.sql.functions.{current_date, lit, struct, to_json, udf}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.io.File
import java.time.format.DateTimeFormatter
import scala.util.Random


object Simple{
  private var counter: Long = 0
  val label: String = "CounterLabel"

  def get(): Long = {
    counter += 1
    return counter
  }

  def strValUDF = udf { () =>
    "{\"id\": %d, \"name\": \"name%d\", \"created_date\": \"%s\",\"created_dt\": \"%s\", \"val\": %.2f}".formatLocal(java.util.Locale.US,
      IdIncrementor.get(),
      Random.nextInt(20),
      DateTimeFormatter.ofPattern("YYYY-MM-dd").format(java.time.LocalDate.now),
      DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS").format(java.time.LocalDateTime.now),
      Random.nextFloat() * 100)
  }

  def writeToKafkaSink(df: DataFrame, bootstrapServer: String, topicName: String, ckpPath: String) = {
    val kafkaParams = Map(
      "kafka.bootstrap.servers" -> bootstrapServer,
      "topic" -> topicName
    )

    df.select(strValUDF().as("value"))
      .writeStream
      .options(kafkaParams)
      .format("kafka")
      .option("checkpointLocation", ckpPath)
  }

  def main(args: Array[String]): Unit = {

    val logger = LogManager.getLogger("TestStreaming")

    var config = ConfigFactory.load("default.conf")
    if (args.length > 0)
      config = ConfigFactory.parseFile(new File(args(0)))

    logger.info("=================")
    logger.info("==   GO!")
    logger.info("=================")

    val spark = SparkSession
      .builder()
      .appName(config.getString("appName"))
      .getOrCreate()

    val df = spark.readStream
      .format("rate")
      .option("rowsPerSecond", config.getInt("rowsPerSecond")).load;

    val sink = writeToKafkaSink(df,
      config.getString("kafka.server"),
      config.getString("kafka.topic"),
      config.getString("chk_point_path"))

    val sq = sink.start

    sq.awaitTermination()

    logger.info("===============")
    logger.info("==   DONE")
    logger.info("===============")

    spark.stop()
  }
}