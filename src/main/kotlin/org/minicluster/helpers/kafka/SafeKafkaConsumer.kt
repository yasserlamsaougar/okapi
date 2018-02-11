package org.minicluster.helpers.kafka;

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.minicluster.helpers.config.ConfigHelper
import java.util.*

class SafeKafkaConsumer(val kodein: Kodein) {

    private val configHelper: ConfigHelper = kodein.instance()
    private val properties: Properties = Properties()
    private val consumers: MutableList<Consumer> = mutableListOf()

    init {
        with(properties) {
            setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configHelper.servicesConfig.kafkaBrokers().joinToString())
            setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL")
            setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
            setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer::class.java.canonicalName)
            setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-${System.currentTimeMillis()}")
            setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "client-${System.currentTimeMillis()}")
            setProperty("ssl.truststore.location", configHelper.servicesConfig.trustStore().toString())
            setProperty("ssl.truststore.password", configHelper.servicesConfig.trustStorePassword())
        }
        (0 until 9).forEach {
            consumers.add(Consumer(false, createConsumer()))
        }
    }

    fun findInMessages(topic: String, partition: Int, startOffset: Long, endOffset: Long = Long.MAX_VALUE, filter: (ConsumerRecord<String, String>) -> Boolean = { true }): List<ConsumerRecord<String, String>> {
        val consumer: Consumer = getConsumer()
        val c = consumer.kafkaConsumer
        val partitionAsList = listOf(c.partitionsFor(topic).map {
            TopicPartition(it.topic(), it.partition())
        }[partition])
        c.assign(partitionAsList)
        val validEndOffset = c.endOffsets(partitionAsList).values.map {
            Math.min(endOffset, it)
        }[partition]
        val beginningOffset = c.beginningOffsets(partitionAsList).values.map { v ->
            Math.min(startOffset + v, validEndOffset)
        }[partition]
        partitionAsList.forEach { p ->
            c.seek(p, beginningOffset)
        }
        var currentSize = 0
        val results = mutableListOf<ConsumerRecord<String, String>>()
        val maxResults = validEndOffset - beginningOffset
        while (currentSize < (validEndOffset - beginningOffset)) {
            val records = c.poll(200)
            results.addAll(records.filter(filter))
            currentSize += records.count()
        }
        consumer.used = false
        return results.take(maxResults.toInt())
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
