ford otosan big data project

## **How to build**

First you have to create jar file of spark app with the command;
````
sbt assembly 
````


## **Go App**
This app is created to connect rabbitmq to the kafka. 

create docker image of this project before running pipeline. 

https://github.com/alpersimsek1/RabbitmqToKafkaGoExample

## **Docker compose**

Check docker compose file before running it. 

You have to change volumes of spark-master and go-app. 

Also, the environment configuration of the spark-workers. 

Also, copy "init.cql" into desired directory and change volumes of cassandra for creating keyspace and tables at the creation of container.

```
docker-compose -p spark -f docker-compose.yml up -d --remove-orphans

docker-compose -p spark -f docker-compose.yml down -v --remove-orphans
```


## **Spark Submit**
You have to change the num-executors, executor-cores, executor-memory before deploying 
the app according to the local machines configurations. 

````
docker cp jarpath:spark-master/path
or mount volume
````
```
./spark/bin/spark-submit --num-executors 6 --executor-cores 1 --executor-memory 1G --class com.proente.Streaming.KafkaStreamer --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0 --master spark://spark-master:7077 MorpheusML-assembly-0.1.jar
```

## **Spark Master**

```
spark://spark-master:7077 
```

**ui**

```
http://localhost:8080/
```

## **Cassandra**

### **Note:**
*With the updated docker-compose.yml, below scripts are automatically run if volumes are set accordingly. If you faced with problems, you can use them manually.*

````
cqlsh -u cassandra -p cassandra
````

**Create Keyspace**
````
create keyspace ford with replication = {       
 'class' : 'SimpleStrategy', 'replication_factor' :1
  } ;
````

**Create Table**
```
CREATE TABLE ford.machine (
machine_id int, type int, is_finish boolean, process_status int,
  timestamp bigint, primary key (machine_id,timestamp) 
);
```

```
CREATE TABLE ford.parsed_machine_data (
machine_id text, type test, is_finish text, process_status text,
  timestamp text, primary key (machine_id,timestamp) 
);
```

```
CREATE TABLE ford.machine_data (value text, primary key (value) );
```




