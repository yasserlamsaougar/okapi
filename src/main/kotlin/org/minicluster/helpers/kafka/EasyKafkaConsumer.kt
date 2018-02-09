package org.minicluster.helpers.kafka

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
import java.util.concurrent.CopyOnWriteArrayList

class EasyKafkaConsumer(val kodein: Kodein) {

    private val configHelper: ConfigHelper = kodein.instance()
    private val consumerDelay = 500L
    private val properties: Properties = Properties()
    private val consumers = CopyOnWriteArrayList<KafkaConsumer<String, String>>()

    init {
        with(properties) {
            setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configHelper.servicesConfig.kafkaBrokers().joinToString())
            setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL")
            setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
            setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer::class.java.canonicalName)
            setProperty("ssl.truststore.location", configHelper.servicesConfig.trustStore().toString())
            setProperty("ssl.truststore.password", configHelper.servicesConfig.trustStorePassword())
        }
        Runtime.getRuntime().addShutdownHook(Thread {
            consumers.forEach(KafkaConsumer<String, String>::wakeup)
            Thread.sleep(500)
        })
    }


    fun runConsumer(topics: List<String>): Flowable<ConsumerRecord<String, String>> {
        val consumer = createConsumerAndSubscribe(topics)
        consumers.add(consumer)
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
                consumer.close()
            }
        }
    }

    private fun createConsumerAndSubscribe(topics: List<String>): KafkaConsumer<String, String> {
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-${topics.joinToString("-")}")
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "client-${System.currentTimeMillis()}")
        val consumer = KafkaConsumer<String, String>(properties)
        consumer.subscribe(topics)
        return consumer
    }
}
