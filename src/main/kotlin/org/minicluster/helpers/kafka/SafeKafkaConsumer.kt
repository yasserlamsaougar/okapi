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
    val properties: Properties = Properties()

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
    }

    fun getFirstOffsets(topic: String, consumer: KafkaConsumer<String, String> = createConsumer()): Map<TopicPartition, Long> {
        consumer.use { c ->
            val partitions = c.partitionsFor(topic).map {
                TopicPartition(it.topic(), it.partition())
            }
            return c.beginningOffsets(partitions)
        }
    }

    fun getEndOffsets(topic: String, consumer: KafkaConsumer<String, String> = createConsumer()): Map<TopicPartition, Long> {
        consumer.use { c ->
            val partitions = c.partitionsFor(topic).map {
                TopicPartition(it.topic(), it.partition())
            }
            return c.endOffsets(partitions)
        }
    }

    fun getMessagesBetween(topic: String, partition: Int, startOffset: Long, endOffset: Long = Long.MAX_VALUE): List<ConsumerRecord<String, String>> {
        createConsumer().use { c ->
            val partitionAsList = listOf(c.partitionsFor(topic).map {
                TopicPartition(it.topic(), it.partition())
            }[partition])
            c.assign(partitionAsList)
            val endOffset = c.endOffsets(partitionAsList).values.map {
                Math.min(endOffset, it)
            }[partition]
            val beginningOffset = c.beginningOffsets(partitionAsList).values.map { v ->
                Math.min(startOffset + v, endOffset)
            }[partition]
            partitionAsList.forEach { p ->
                c.seek(p, beginningOffset)
            }
            val results = mutableListOf<ConsumerRecord<String, String>>()
            val maxResults = endOffset - beginningOffset
            while (results.size < (endOffset - beginningOffset)) {
                val records = c.poll(50)
                results.addAll(records)
            }
            return results.take(maxResults.toInt())
        }

    }

    fun findInMessages(topic: String, partition: Int, startOffset: Long, endOffset: Long = Long.MAX_VALUE, filter: (ConsumerRecord<String, String>) -> Boolean): List<ConsumerRecord<String, String>> {
        return getMessagesBetween(topic, partition, startOffset, endOffset).filter(filter)
    }

    private fun createConsumer(): KafkaConsumer<String, String> {
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-${System.currentTimeMillis()}")
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "client-${System.currentTimeMillis()}")
        return KafkaConsumer(properties)
    }

}
