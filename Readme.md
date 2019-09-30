# Ford Otosan Big Data Project

This project is mainly developed for Ford Otosan, but will be generalized and 
converted into an easily usable form for different projects.

## How to build

First you have to create jar file of spark app with the command;
`sbt assembly`

### Go App

This app is created to connect rabbitmq to the kafka. Create docker image of 
this project before running pipeline.

[RabbitMQToKafkaGoExample](https://github.com/alpersimsek1/RabbitmqToKafkaGoExample)

### Docker compose

* Check docker compose file before running it.

* You have to change volumes of spark-master and go-app.

* You can specify topics that will be created after build inside 
`docker-compose.yml` file by setting `KAFKA_CREATE_TOPICS`. Desired format is 
`"topicName:#partition:#replication"`.

  * **_Example Syntax:_** `KAFKA_CREATE_TOPICS: "test:1:1"`

* You should adjust the environment configuration of the spark-workers.

* **Optional:** You can copy `init.cql` into desired directory and change 
volumes of cassandra for creating keyspace and tables at the creation of 
container. If `init.cql` is used, table and keyspace names should be set 
accordingly.

* #### _Commands_

  ```bash
  docker-compose -p spark -f docker-compose.yml up -d --remove-orphans

  docker-compose -p spark -f docker-compose.yml down -v --remove-orphans
  ```
  
  or you can use scaling feature of docker-compose. As an example, below 
  command creates 3 kafka containers.
  ```bash
  docker-compose -p spark -f docker-compose.yml up --scale kafka=3 -d --remove-orphans
  ```

### Spark Submit

You have to change the num-executors, executor-cores, executor-memory before 
deploying the app according to the local machines configurations.

**_Note:_** You don't need to copy jar file if you have already set volume in 
compose file. If you want to copy file manually, you should run below command.

```bash
docker cp jarpath:spark-master/path
# or mount volume
```

After that, you can run app with the command below inside docker container.

```bash
./spark/bin/spark-submit --num-executors 6 --executor-cores 1 --executor-memory 1G --class com.proente.Streaming.KafkaStreamer --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0 --master spark://spark-master:7077 MorpheusML-assembly-0.1.jar
```

### Spark Master

* **_master:_** `spark://spark-master:7077`

* **_ui:_** `http://localhost:8080/`

### Cassandra

***Note***: *With the updated docker-compose.yml, below scripts are 
automatically run if volumes are set accordingly. If you faced with problems, 
you can use them manually.*

```cassandra
cqlsh -u cassandra -p cassandra
```

* #### Create Keyspace

```cassandra
create keyspace ford with replication = {
 'class' : 'SimpleStrategy', 'replication_factor' :1
  } ;
```

* #### Create Tables

```cassandra
CREATE TABLE ford.machine (
machine_id int, type int, is_finish boolean, process_status int,
  timestamp bigint, primary key (machine_id,timestamp)
);
```

```cassandra
CREATE TABLE ford.parsed_machine_data (
machine_id text, type test, is_finish text, process_status text,
  timestamp text, primary key (machine_id,timestamp)
);
```

```cassandra
CREATE TABLE ford.machine_data (value text, primary key (value) );
```
