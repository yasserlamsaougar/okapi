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
    val zkUtils: ZkUtils

    init {
        zkUtils = ZkUtils.apply(configHelper.servicesConfig.zookeeperConnectionString(), 3000, 3000, true)
    }

    fun listTopics(): List<String> {
        return JavaConversions.seqAsJavaList(zkUtils.allTopics)
    }

    fun deleteTopics(vararg topics: String) : List<String>{
        val listOfDeleted = mutableListOf<String>()
        topics.forEach { topic ->
            if(AdminUtils.topicExists(zkUtils, topic)) {
                zkUtils.deletePathRecursive(ZkUtils.getTopicPath(topic))
                listOfDeleted.add(topic)
            }
        }
        return listOfDeleted
    }

    fun createTopics(vararg topics: String) : List<String> {
        val listOfCreated = mutableListOf<String>()
        topics.forEach { topic ->
            if(!AdminUtils.topicExists(zkUtils, topic)) {
                AdminUtils.createTopic(zkUtils, topic, 1, 1, Properties(), RackAwareMode.`Enforced$`.`MODULE$`)
                listOfCreated.add(topic)
            }
        }
        return listOfCreated
    }

}