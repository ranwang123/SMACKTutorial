#SMACK example

This is a simple example that ingest a stream of words from Akka stream into 
Kafka, read the stream into Spark and save it to Cassandra


##Set up environment
The following is the steps needed to set up a testing environment. 

Caution: if you have already set up an environment, be sure to check the versions
are consistent with the ones offered in this package. 

###Download Spark-2.0.0
One can obtain Spark-2.0.0 from <https://spark.apache.org/downloads.html>. 
After extracting files, export the path to the ``bin`` folder using the following 
command (suppose that the extract file is located at ``/home/user/Desktop/software/spark-2.0.0-bin-hadoop2.7
``) 

```shell
export PATH="/home/user/Desktop/software/spark-2.0.0-bin-hadoop2.7/bin:$PATH"
```

###Download Cassandra-3.0.14 
Can be obtained from <https://archive.apache.org/dist/cassandra/>. Extract the files.

###Download Kafka_2.11-0.11.0.1
Can be obtained from <https://kafka.apache.org/downloads>.Extract the files.

##To run the example
Cd into the root directory of the project, after typing ``sbt``, type the 
following commands

```sbtshell
compile
package
assembly
```

Start Kafka server. Cd into the root directory of the Kafka files. 
Type the following commands:

```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
bin/kafka-topics.sh	--create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic smack01
```

Now start Cassandra server. Cd into the bin directory of Cassandra, and type the 
following commands:

```shell
./cassandra -f
```

In the same folder, start ``cqlsh`` with the following command:
```shell
./cqlsh
```
In the shell, type the following commands:

```sql
CREATE KEYSPACE test WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
USE test;
CREATE TABLE smack01(
id int PRIMARY KEY,
value text
   );
```

Now we are ready to run the package. First cd into the ``Producer`` directory and type
``sbt run``. Next cd into the ``Consumer`` directory and type:

```shell
spark-submit target/scala-2.11/SMACK01Consumer-assembly-1.0.jar
```