package com.demo
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

trait SparkSessionWrapper {

  val local:Boolean

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