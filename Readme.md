ford otosan big data project

**Spark Submit**

````
docker cp jarpath:spark-master/path
or mount volume
````
```
./spark/bin/spark-submit --num-executors 6 --executor-cores 1 --executor-memory 1G --class com.proente.Strming.KafkaStreamer --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0 --master spark://spark-master:7077 MorpheusML-assembly-0.1.jar
```

**Spark Master**

```spark://spark-master:7077 ```

*ui*

```http://localhost:8080/```