package org.minicluster.splitters.tasks

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.kafka.KafkaHelper
import org.minicluster.helpers.kafka.SafeKafkaConsumer
import org.minicluster.services.KafkaService

class KafkaSplittableTask(override val arguments: Map<String, Any?>, override val kodein: Kodein) : SplittableTask<List<KafkaService.Record>> {

    val configHelper: ConfigHelper = kodein.instance()
    val kafkaHelper: SafeKafkaConsumer = kodein.instance()
    override fun merge(tasksResults: List<List<KafkaService.Record>>): List<KafkaService.Record> {
        return tasksResults.flatten()
    }

    override fun split(): List<SplittableTask<List<KafkaService.Record>>> {
        val from = arguments["from"] as Long
        val to = arguments["to"] as Long
        val topic = arguments["topic"] as String
        val step = Math.max((to - from) / configHelper.servicesConfig.consumerPoolSize(), 1)
        val partitions = kafkaHelper.getPartitions(topic)
        return (from until to step step).map { index ->
            partitions.map {
                KafkaSplittableTask(mapOf(
                        "from" to index,
                        "to" to index + step,
                        "partition" to it
                ), kodein)
            }
        }.flatten()
    }

}