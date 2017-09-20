import sbt._
import Keys._


object HelloBuild extends Build {

  lazy val root = Project(id = "smack01",
                          base = file(".")) aggregate(producer, consumer) dependsOn(producer, consumer)
  lazy val producer = Project(id = "producer",
                         base = file("Producer"))
  lazy val consumer = Project(id = "consumer",
                         base = file("Consumer"))
}
