#Dependency List

This is a file where possible libraries to be included in projects are documented. 

The libraries are ordered by alphabet. 

###Akka actors and streams
```scala
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.17"
libraryDependencies += "com.typesafe.akka" % "akka-stream_2.11" % "2.4.17"
libraryDependencies += "com.lightbend.akka" % "akka-stream-alpakka-amqp_2.11" % "0.5"
libraryDependencies += "com.typesafe.akka" % "akka-stream-kafka_2.11" % "0.13"
```

###Basic libraries

```scala
libraryDependencies ++= Seq(
			"com.softwaremill.scalamacrodebug" %% "macros" % "0.4",
			"com.github.tototoshi" %% "scala-csv" % "1.3.5",
			"org.scalaz" %% "scalaz-core" % "7.2.15",
			"com.typesafe.play" % "play-json_2.11" % "2.6.5"
			)
```
###Initialization script for spark shell
```scala

initialCommands in console := """
  import org.apache.spark.SparkContext
  import org.apache.spark.SparkConf
  import org.apache.spark.sql.SparkSession
  import org.apache.spark.sql.functions._
  import org.apache.spark.sql.SQLContext
  val conf = new SparkConf().setMaster("local[*]").setAppName("SparkShell").set("spark.executor.memory", "6g").set("spark.driver.memory", "6g").set("spark.driver.maxResultSize", "10g")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
"""

cleanupCommands in console := "sc.stop()"
```

###Kafka 
```scala
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.1"
```

###Stanford NLP tools
Be careful to include in assembly as the package is very large. 
```scala
libraryDependencies ++= Seq(
  "edu.stanford.nlp" % "stanford-corenlp" % "3.4",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.4" classifier "models",
  "edu.stanford.nlp" % "stanford-parser" % "3.4"
)
```
