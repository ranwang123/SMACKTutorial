#SMACK example

This is a simple example that ingest a stream of words from Akka stream into 
Kafka, read the stream into Spark and save it to Cassandra

After compiling, follow the following steps.

###Configure and start Kafka server

```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
bin/kafka-topics.sh	--create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic smack01
```

Note that in this stage, if one wants to read the producer record sent, 
one can use the following command. 

```sbtshell
bin/kafka-console-consumer.sh --zookeeper localhost:2181  --topic smack01
```

