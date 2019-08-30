name := "MorpheusML"

version := "0.1"

scalaVersion := "2.12.0"

val sparkVersion = "2.4.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion, // % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion, // % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion, // % "provided" ,
  "org.apache.spark" %% "spark-hive" % sparkVersion, // % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion // % "provided"
)