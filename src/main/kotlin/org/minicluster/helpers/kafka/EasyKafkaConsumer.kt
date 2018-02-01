package org.minicluster.helpers.kafka;

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.rx2.rxFlowable
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.minicluster.helpers.config.ConfigHelper
import java.util.*

class EasyKafkaConsumer(val kodein: Kodein) {

    private val configHelper: ConfigHelper = kodein.instance()
    private val kafkaConsumer: KafkaConsumer<String, String>
    private val consumerDelay = 500L
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
        kafkaConsumer = KafkaConsumer(properties)
        Runtime.getRuntime().addShutdownHook(Thread {
            kafkaConsumer.wakeup()
            Thread.sleep(500)
        })
    }

    fun subscribe(topics: List<String>) {
        kafkaConsumer.subscribe(topics)
    }


    fun runConsumer(): Flowable<ConsumerRecord<String, String>> {
        val consumer = kafkaConsumer
        return rxFlowable {
            try {
                while (!isClosedForSend && isActive) {
                    val records = consumer.poll(consumerDelay)
                    records.forEach {
                        send(it)
                    }
                    consumer.commitAsync()
                }
            }
            catch (e:WakeupException) {
            }
            finally {
                kafkaConsumer.close()
            }
        }
    }
}
