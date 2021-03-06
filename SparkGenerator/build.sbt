name := "SparkGenerator"

version := "0.1"

scalaVersion := "2.12.15"

val sparkVersion = "3.0.3"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion ,
  "org.apache.spark" %% "spark-token-provider-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.kafka" % "kafka-clients" % "3.0.0",
  "org.apache.commons" % "commons-pool2" % "2.11.1",
  "com.typesafe" % "config" % "1.4.1"
)

