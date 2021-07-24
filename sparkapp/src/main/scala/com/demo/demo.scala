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

  val fileType = ".csv"
  val log:Logger = LogManager.getRootLogger()
  val inputFilePath:String = s"${AppConfig.getDataRoot()}example$fileType"
  val spark: SparkSession = getSparkSession()

  log.warn(AppConfig.toString())

  val options = Map(
    "header" -> "false",
    "inferschema" -> "true",
    "delimiter" -> ","
  )

  try 
  {

    log.info(s"Loading file: $inputFilePath")

    val df:DataFrame = Try(
      spark.read
        .options(options)
        .csv(inputFilePath)
    ) 
    match {
      case Success(s) => s
      case Failure(f) => {
        log.error(s"failed to load ${fileType} file from $inputFilePath")
        throw new IOException(s"failed to load ${fileType} file from $inputFilePath")
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