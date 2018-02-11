package org.minicluster.services

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.ApiBuilder.*
import io.javalin.Context
import io.javalin.Javalin
import io.javalin.translator.json.JavalinJacksonPlugin
import org.apache.http.HttpStatus
import org.minicluster.helpers.kafka.EasyKafkaProducer
import org.minicluster.helpers.kafka.KafkaHelper
import org.minicluster.helpers.kafka.SafeKafkaConsumer

class KafkaService(val kodein: Kodein) : Service {

    private val kafkaHelper: KafkaHelper = kodein.instance()
    private val safeKafkaConsumer: SafeKafkaConsumer = kodein.instance()
    private val easyKafkaProducer: EasyKafkaProducer = kodein.instance()
    override fun setup(app: Javalin) {
        app.routes {
            path("kafka") {
                path("topic") {
                    post(this::createTopic)
                    get(this::listTopics)
                    path(":name") {
                        delete(this::deleteTopic)
                        get(this::readTopic)
                        path("_search") {
                            get(this::searchTopic)
                        }
                        post(this::sendMessage)
                    }
                }
            }
        }
    }

    fun createTopic(ctx: Context) {
        val topic = ctx.bodyAsClass(Topic::class.java)
        val created = kafkaHelper.createTopics(topic.name, partitions = topic.partitions, replicationFactor = topic.replicationFactor)
        if (created.isEmpty()) {
            ctx.status(HttpStatus.SC_CONFLICT)
        } else {
            ctx.status(HttpStatus.SC_CREATED)
        }
        ctx.json(topic)
    }

    fun listTopics(ctx: Context) {
        ctx.json(kafkaHelper.listDetailedTopics())
    }

    fun deleteTopic(ctx: Context) {
        val topic = ctx.param(":name")
        val deleted = kafkaHelper.deleteTopics(topic!!)
        if (deleted.isEmpty()) {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "$topic not found"
            ))
        } else {
            ctx.status(HttpStatus.SC_OK)
        }
    }

    fun readTopic(ctx: Context) {
        val name = ctx.param("name").orEmpty()
        val partition = ctx.queryParam("partition")?.toInt()!!
        val from = ctx.queryParamOrDefault("from", "0").toLong()
        val to = ctx.queryParamOrDefault("to", Long.MAX_VALUE.toString()).toLong()
        val records = safeKafkaConsumer.findInMessages(topic = name, partition = partition, startOffset = from, endOffset = to)
                .map {
                    Record(it.topic(), it.partition(), it.key().orEmpty(), it.value(), it.offset())
                }
        ctx.json(records)
    }

    fun searchTopic(ctx: Context) {
        val name = ctx.param("name").orEmpty()
        val partition = ctx.queryParam("partition")?.toInt()!!
        val from = ctx.queryParamOrDefault("from", "0").toLong()
        val to = ctx.queryParamOrDefault("to", Long.MAX_VALUE.toString()).toLong()
        val keyword = ctx.queryParam("query").orEmpty()
        val records = safeKafkaConsumer.findInMessages(topic = name, partition = partition, startOffset = from, endOffset = to)
        {
            it.value().contains(keyword)
        }.map {
                    Record(it.topic(), it.partition(), it.key().orEmpty(), it.value(), it.offset())
                }
        ctx.json(records)
    }

    fun sendMessage(ctx: Context) {
        val name = ctx.param("name").orEmpty()
        val message= ctx.bodyAsClass(Message::class.java)
        easyKafkaProducer.produce(name, key = message.key, message = JavalinJacksonPlugin.toJson(message.text))
        ctx.status(HttpStatus.SC_OK)

    }
    data class Message(val key: String? = null, val text: Any)
    data class Topic(val name: String, val partitions: Int, val replicationFactor: Int)
    data class Record(val topic: String, val partition: Int, val key: String, val message: String, val offset: Long)
}