package com.demo
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.DataFrame
import org.apache.log4j.{LogManager, Logger}
import scala.util.{Try,Success,Failure}
import java.io.FileNotFoundException
import java.io.IOException
import com.demo.{Configuration => AppConfig}
import org.apache.spark.sql.SaveMode

object Demo extends App with SparkSessionWrapper{


  val log:Logger = LogManager.getRootLogger()
  val inputFilePath:String = AppConfig.getDataRoot() + "example.json"
  val spark: SparkSession = getSparkSession()

  log.warn(AppConfig.toString())

  try 
  {

    log.warn(s"Loading file: $inputFilePath")
    val df:DataFrame = Try(spark.read.json(inputFilePath)) 
    match {

      case Success(s) => s
      case Failure(f) => {

        log.error(s"failed to load JSON file from $inputFilePath")
        throw new IOException(s"failed to load JSON file from $inputFilePath")

      }
    }

    df.show()

    val outputPath = s"${AppConfig.getDataRoot()}/out"
    df.write.mode(SaveMode.Overwrite).parquet(outputPath)

  }
  finally
  {

    spark.stop()

  }

}