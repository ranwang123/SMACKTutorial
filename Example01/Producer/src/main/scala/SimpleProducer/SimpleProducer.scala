package SimpleProducer

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, _}
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.util.ByteString
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

object SimpleProducer extends App {
  implicit val actorSystem = ActorSystem("SimpleStream")
  implicit val actorMaterializer = ActorMaterializer()

  val bootstrapServers = "localhost:9092"
  val kafkaTopic = "smack01"
  val partition = 0

  val producerSettings = ProducerSettings(actorSystem, new StringSerializer, new StringSerializer)
    .withBootstrapServers(bootstrapServers)


  val lineByLineSource = FileIO.fromPath(Paths.get(getClass.getClassLoader.getResource("fakedata.csv").getPath))
    .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 1024))
    .map(_.utf8String)
//  println(getClass.getClassLoader.getResource("fakedata.csv").getPath)
//  val printlnSink = Sink.foreach(println)
//  val test = lineByLineSource.to(printlnSink)
  val runnableGraph = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._

    val kafkaSink = Producer.plainSink(producerSettings)
    val mapToProducerRecord = Flow[String].map(elem => new ProducerRecord[String, String](kafkaTopic, elem))

    lineByLineSource  ~> mapToProducerRecord   ~> kafkaSink

    ClosedShape
  })

  runnableGraph.run()

}

