name := "SMACK01Producer"
version := "1.0"
scalaVersion := "2.11.1"


libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.17"
libraryDependencies += "com.typesafe.akka" % "akka-stream_2.11" % "2.4.17"
libraryDependencies += "com.lightbend.akka" % "akka-stream-alpakka-amqp_2.11" % "0.5"
libraryDependencies += "com.typesafe.akka" % "akka-stream-kafka_2.11" % "0.13"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.1"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "0.10.2.1"
