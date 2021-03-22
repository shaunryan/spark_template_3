package com.demo
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.DataFrame
import org.apache.log4j.{LogManager, Logger}
import scala.util.{Try,Success,Failure}
import java.io.FileNotFoundException
import java.io.IOException

object DatabricksScalaTutorial extends App with SparkSessionWrapper{


  val log:Logger = LogManager.getRootLogger()

  val local:Boolean = args(0).toBoolean
  val inputFilePath:String = args(1)
  val spark: SparkSession = getSparkSession(local)

  log.info(s"filpath:$inputFilePath local:${local.toString()}")

  try 
  {

    val df:DataFrame = Try(spark.read.json(inputFilePath)) 
    match {

      case Success(s) => s
      case Failure(f) => {

        log.error(s"failed to load JSON file from $inputFilePath")
        throw new IOException(s"failed to load JSON file from $inputFilePath")

      }
    }

    df.show()

    val outputPath = s"${inputFilePath}/out"
    df.write.parquet(outputPath)

  }
  finally
  {

    spark.stop()

  }

}