package org.minicluster.helpers.kafka

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import kafka.admin.AdminUtils
import kafka.admin.RackAwareMode
import kafka.utils.ZkUtils
import org.minicluster.helpers.config.ConfigHelper
import scala.collection.JavaConversions
import java.util.*

class KafkaHelper(val kodein: Kodein) {
    val configHelper: ConfigHelper = kodein.instance()
    val zkUtils: ZkUtils by lazy {
        ZkUtils.apply(configHelper.servicesConfig.zookeeperConnectionString(), 3000, 3000, true)
    }


    fun listTopics(): List<String> {
        return JavaConversions.seqAsJavaList(zkUtils.allTopics)
    }

    fun listDetailedTopics(): List<TopicMetadata> {
        val topics = JavaConversions.asScalaSet(listTopics().toSet())
        return JavaConversions.asJavaSet(AdminUtils.fetchTopicMetadataFromZk(topics, zkUtils)).map {
            val partitionsMetadata = it.partitionMetadata()
                    .map { part ->
                        PartitionMetadata(part.partition(), part.leader().id(), part.error().message(), part.replicas().size, part.isr().size)
                    }
            TopicMetadata(it.topic(), it.error().message(), partitionsMetadata)
        }
    }

    fun deleteTopics(vararg topics: String): List<String> {
        val listOfDeleted = mutableListOf<String>()
        topics.forEach { topic ->
            if (AdminUtils.topicExists(zkUtils, topic)) {
                zkUtils.deletePathRecursive(ZkUtils.getTopicPath(topic))
                listOfDeleted.add(topic)
            }
        }
        return listOfDeleted
    }

    fun createTopics(vararg topics: String, partitions: Int = 1, replicationFactor: Int = 1): List<String> {
        val listOfCreated = mutableListOf<String>()
        topics.forEach { topic ->
            if (!AdminUtils.topicExists(zkUtils, topic)) {
                AdminUtils.createTopic(zkUtils, topic, partitions, replicationFactor, Properties(), RackAwareMode.`Enforced$`.`MODULE$`)
                listOfCreated.add(topic)
            }
        }
        return listOfCreated
    }

    data class TopicMetadata(val name: String, val errors: String, val partitions: List<PartitionMetadata>)
    data class PartitionMetadata(val index: Int, val leaderNode: Int, val errors: String, val replicas: Int, val isr: Int)
}