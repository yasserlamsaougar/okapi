package org.minicluster.helpers.kafka

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.minicluster.helpers.config.ConfigHelper
import java.util.*

class EasyKafkaProducer(val kodein: Kodein) {

    private val configHelper: ConfigHelper = kodein.instance()
    private val kafkaProducer: KafkaProducer<String, String>
    val properties: Properties = Properties()

    init {
        with(properties) {
            setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configHelper.servicesConfig.kafkaBrokers().joinToString())
            setProperty(ProducerConfig.RETRIES_CONFIG, "1")
            setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL")
            setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
            setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
            setProperty("ssl.truststore.location", configHelper.servicesConfig.trustStore().toString())
            setProperty("ssl.truststore.password", configHelper.servicesConfig.trustStorePassword())
        }
        kafkaProducer = KafkaProducer(properties)
    }

    fun produce(vararg topics: String, key: String? = null, message: String) {
        topics.map {
            ProducerRecord<String, String>(it, key, message)
        }.forEach { kafkaProducer.send(it).get() }
    }
}