ford otosan big data project

**How to build**

````
sbt assembly 
````

**Docker compose**

```
docker-compose -p spark -f docker-compose.yml up -d --remove-orphans
docker-compose -p spark -f docker-compose.yml down -v --remove-orphans
```


**Spark Submit**

````
docker cp jarpath:spark-master/path
or mount volume
````
```
./spark/bin/spark-submit --num-executors 6 --executor-cores 1 --executor-memory 1G --class com.proente.Streaming.KafkaStreamer --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0 --master spark://spark-master:7077 MorpheusML-assembly-0.1.jar
```

**Spark Master**

```
spark://spark-master:7077 
```

*ui*

```
http://localhost:8080/
```

**Cassandra**

````
cqlsh -u cassandra -p cassandra

create keyspace ford with replication = {       
 'class' : 'SimpleStrategy', 'replication_factor' :1
  } ;

CREATE TABLE ford.machine_data (value text, primary key (value) );
````




