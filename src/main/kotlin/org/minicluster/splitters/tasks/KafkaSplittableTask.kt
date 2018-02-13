package org.minicluster.splitters.tasks

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.services.KafkaService

class KafkaSplittableTask(override val arguments: Map<String, Any?>, override val kodein: Kodein) : SplittableTask<List<KafkaService.Record>> {

    val configHelper: ConfigHelper = kodein.instance()
    override fun merge(tasksResults: List<List<KafkaService.Record>>): List<KafkaService.Record> {
        return tasksResults.flatten()
    }

    override fun split(): List<SplittableTask<List<KafkaService.Record>>> {
        val from = arguments["from"] as Long
        val to = arguments["to"] as Long
        val step = Math.max((to - from) / configHelper.servicesConfig.consumerPoolSize(), 1)

        return (from..(to) step step).map {
            KafkaSplittableTask(mapOf(
                    "from" to it,
                    "to" to it + step
            ), kodein)
        }
    }

}