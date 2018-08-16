package org.minicluster.services.realtime

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.Javalin
import io.javalin.embeddedserver.jetty.websocket.WsSession
import io.javalin.translator.json.JavalinJacksonPlugin
import io.reactivex.disposables.Disposable
import org.minicluster.helpers.kafka.EasyKafkaConsumer
import org.minicluster.services.Service

class KafkaRealtimeService(val kodein: Kodein) : Service {

    private val easyKafkaConsumer: EasyKafkaConsumer = kodein.instance()
    private val subscriptionMap: MutableMap<String, Disposable> = mutableMapOf()
    private val topicsSessions: MutableMap<String, MutableMap<String, WsSession>> = mutableMapOf()
    override fun setup(app: Javalin) {
        app.ws("kafka/topic/:topic-id") { ws ->
            ws.onConnect { session ->
                val topicId = session.param("topic-id").orEmpty()
                topicsSessions.putIfAbsent(topicId, mutableMapOf())
                topicsSessions[topicId]!!.putIfAbsent(session.id, session)
                if (!subscriptionMap.containsKey(topicId)) {
                    subscriptionMap.putIfAbsent(topicId, easyKafkaConsumer.runConsumer(listOf(topicId)).subscribe { record ->
                        val recordMap = JavalinJacksonPlugin.toJson(mapOf(
                                "partition" to record.partition(),
                                "value" to record.value(),
                                "topic" to record.topic(),
                                "timestamp" to record.timestamp()
                        ))
                        topicsSessions[topicId]!!.values.parallelStream().forEach {
                            it.send(recordMap)
                        }
                    })
                }
            }
            ws.onMessage { session, msg ->
                println(msg)
                print("message")
                session.send(JavalinJacksonPlugin.toJson("fuck you back"))
            }

            ws.onClose { session, _, _ ->
                val topicId = session.param("topic-id").orEmpty()
                if (topicsSessions.containsKey(topicId)) {
                    val sessionsMap = topicsSessions[topicId]!!
                    if (sessionsMap.containsKey(session.id)) {
                        sessionsMap.remove(session.id)
                    }
                }
            }
        }
    }
}
