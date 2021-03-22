package com.demo
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.DataFrame
import org.apache.log4j.{LogManager, Logger}

object DatabricksScalaTutorial extends App{

  val log:Logger = LogManager.getRootLogger()

  val local:Boolean = args(0).toBoolean
  val file_path:String = args(1)
  
  log.warn(s"filpath:$file_path warn:${local.toString()}")

  val spark:SparkSession = getSparkSession(local)
  val df:DataFrame = spark.read.json(file_path)
  df.show()

  def getSparkSession(local:Boolean):SparkSession = 
  {

    val spark:SparkSession = if (local)
    {
      val sparkConfig = new SparkConf()
      sparkConfig.set("spark.broadcast.compress", "false")
      sparkConfig.set("spark.shuffle.compress", "false")
      sparkConfig.set("spark.shuffle.spill.compress", "false")
      SparkSession
        .builder()
        .master("local[2]")
        .appName("Demo")
        .config(sparkConfig)
        .getOrCreate()
    }
    else
    {
      SparkSession
        .builder()
        .appName("Demo")
        .getOrCreate()
    }

    spark

  }

}