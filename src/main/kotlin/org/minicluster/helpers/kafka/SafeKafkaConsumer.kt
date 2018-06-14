package org.minicluster.helpers.kafka

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.minicluster.helpers.config.ConfigHelper
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class SafeKafkaConsumer(val kodein: Kodein) {

    private val configHelper: ConfigHelper = kodein.instance()
    private val properties: Properties = Properties()
    private val consumers: MutableList<Consumer> = CopyOnWriteArrayList<Consumer>()

    init {
        with(properties) {
            setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configHelper.servicesConfig.kafkaBrokers().joinToString())
            setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL")
            setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
            setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
            setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-${System.currentTimeMillis()}")
            setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "client-${System.currentTimeMillis()}")
            setProperty("ssl.truststore.location", configHelper.servicesConfig.trustStore().toString())
            setProperty("ssl.truststore.password", configHelper.servicesConfig.trustStorePassword())
        }
        (0 until configHelper.servicesConfig.consumerPoolSize()).forEach {
            consumers.add(Consumer(false, createConsumer()))
        }
    }
    fun getPartitions(topic: String) : List<Int>{
        val consumer: Consumer = getConsumer()
        val results = consumer.kafkaConsumer.partitionsFor(topic).map {
            it.partition()
        }
        consumer.used = false
        return results
    }

    fun countTopicSize(topic: String) : Long {
        val consumer: Consumer = getConsumer()
        val c = consumer.kafkaConsumer
        val partitionAsList = c.partitionsFor(topic).map {
            TopicPartition(it.topic(), it.partition())
        }.sortedBy { it.partition() }
        c.assign(partitionAsList)
        c.seekToBeginning(partitionAsList)
        val startPositions = partitionAsList.map {c.position(it)}
        c.seekToEnd(partitionAsList)
        val endPositions = partitionAsList.map { c.position(it) }
        val result = startPositions.zip(endPositions).map { pair ->
            pair.second - pair.first
        }.sum()
        consumer.used = false
        return result
    }
    fun findInMessages(topic: String, partition: Int, startOffset: Long, endOffset: Long = Long.MAX_VALUE, filter: (ConsumerRecord<String, String>) -> Boolean): List<ConsumerRecord<String, String>> {
        val consumer: Consumer = getConsumer()
        val c = consumer.kafkaConsumer
        val partitionAsList = listOf(c.partitionsFor(topic).map {
            TopicPartition(it.topic(), it.partition())
        }.sortedBy { it.partition() }[partition])
        c.assign(partitionAsList)
        c.seekToEnd(partitionAsList)
        var validEndOffset = partitionAsList.map {
            c.position(it)
        }.first()
        c.seekToBeginning(partitionAsList)
        val positionFirst = partitionAsList.map { v ->
            c.position(v)
        }.first()
        val beginningOffset = partitionAsList.map { v ->
            Math.min(startOffset + positionFirst, validEndOffset)
        }.first()
        partitionAsList.forEach { p ->
            c.seek(p, beginningOffset)
        }
        validEndOffset = Math.min(positionFirst + endOffset, validEndOffset)

        var currentSize = 0
        val results = mutableListOf<ConsumerRecord<String, String>>()
        val maxResults = validEndOffset - beginningOffset
        while (currentSize < maxResults) {
            val records = c.poll(200)
            results.addAll(records.filter(filter))
            currentSize += records.count()
        }
        consumer.used = false
        return results.take(Math.min(maxResults.toInt(), results.size))
    }

    private fun createConsumer(): KafkaConsumer<String, String> {
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-${System.currentTimeMillis()}")
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "client-${System.currentTimeMillis()}")
        return KafkaConsumer(properties)
    }


    @Synchronized
    private fun getConsumer(): Consumer {
        val firstNotUsed = consumers.find {
            !it.used
        } ?: Consumer(true, createConsumer())
        firstNotUsed.used = true
        return firstNotUsed
    }

    data class Consumer(var used: Boolean, val kafkaConsumer: KafkaConsumer<String, String>)

}
