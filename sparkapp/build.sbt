  
name := "SparkApp"

version := "0.1"

scalaVersion := "2.12.12"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.1.2",
  // "org.apache.spark" %% "spark-streaming" % "3.1.2",
  "org.apache.spark" %% "spark-sql" % "3.1.2",

  // testing dependencies
  "org.scalactic" %% "scalactic" % "3.2.5",
  "org.scalatest" %% "scalatest" % "3.2.5" % "test"
)
