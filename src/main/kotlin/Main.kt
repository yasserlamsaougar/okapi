import org.minicluster.Injector
import org.minicluster.helpers.kafka.KafkaHelper

fun main(args: Array<String>) {

    val injector = Injector()
    val kafkaHelper = KafkaHelper(injector.kodein)
    println(kafkaHelper.listTopics())
    println(kafkaHelper.createTopics("t1", "t2", "t3"))
    println(kafkaHelper.listTopics())
    println(kafkaHelper.deleteTopics("t1", "t2", "t3"))
    println(kafkaHelper.listTopics())
}