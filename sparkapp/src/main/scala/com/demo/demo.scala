package com.demo
import org.apache.spark.sql.SparkSession

object DatabricksScalaTutorial {
  def doStuff() {
    val spark = SparkSession
      .builder()
      .master("local")
      .getOrCreate()
    
    println(spark.range(100).count())

    println("""
    Hello spark!
    """)

  }
 

}