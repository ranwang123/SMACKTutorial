package SimpleConsumer
import Data.Observation._

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object SimpleConsumer {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("SMACK01").setMaster("local[4]")
    conf.setIfMissing("spark.cassandra.connection.host", "127.0.0.1")
    val sc = new SparkContext(conf)

    // streams will produce data every second
    val ssc = new StreamingContext(sc, Seconds(3))
//
    val cassandraKeyspace = "test"
    val cassandraTable = "smack01"

    // Create the stream.
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "Spark stream 01",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("smack01")
    val kafkaStream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    kafkaStream.map(record=>(record.value().toString)).print
    val parsedStream: DStream[Entry] = ingestStream(kafkaStream)
//
    persist(cassandraKeyspace, cassandraTable, parsedStream)
//    val Host = "localhost"
//    val Port = 9999
//    val lines = ssc.socketTextStream(Host, Port)
//    val words = lines.flatMap(_.split(" "))
//    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
//    import com.datastax.spark.connector.streaming._
//    wordCounts.saveToCassandra("test", "words")

    ssc.start()
    ssc.awaitTermination()

  }

  def persist(CassandraKeyspace: String, CassandraTable: String,
              parsedStream: DStream[Entry]): Unit = {

    import com.datastax.spark.connector.streaming._

    parsedStream.saveToCassandra(CassandraKeyspace, CassandraTable)
  }

  def ingestStream(rawStream: DStream[ConsumerRecord[String, String]]): DStream[Entry] = {
    val parsedStream = rawStream.map(_.value().split(","))
      .map(Entry(_))
    parsedStream
  }

}