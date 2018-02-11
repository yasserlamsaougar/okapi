# README #

## Introduction ##
This project contains a set of rest services (and soon websockets) to allow anyone with sufficient access to perform operations on hbase, kafka and hdfs. 

## Tools Used ## 
This project uses the kafka, hbase and hdfs apis to perform the operations on the cluster. It also uses javalin extensively to create the rest services

### List of tools ###

1. Javalin (rest and ws microframework)
2. Gradle (build and packaging system)
3. kodein (dependency injection)
4. cfg4k (configuration)
5. reflections (makes reflections quite easy)

## Installation ##

1. git clone {project_repo}
2. gradle build

## Supported Operations

### Kafka ###

1. Topic creation
2. Topic listing with some details
3. Topic deletion
4. Partition search
5. Message sending

### Hbase ###

1. Table and namespace creation
2. Table deletion
3. Table count with sql filter like queries
4. Table search with sql filter like queries
5. Table listing
6. Data adding in bulk or unit mode

### Hdfs ###

1. Recursive path listing
2. File uploading
3. File downloading
4. File reading
5. Path deletion (recursive as option)

## Next ##

1. Add realtime features to kafka and hbase (priority: highest)

1.1 realtime consumer
1.2 realtime hbase updates (put, delete) 

2. Add detailed report of hdfs,hbase and hdfs cluster (priority: medium)

3. Add support for hive cluster (priority: low)

4. Add support for spark and yarn or mesos (priority: medium)

5. Add support for cassandra (priority: low)

6. Add authentication manager to javalin (priority: high)