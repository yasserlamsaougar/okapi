import com.github.salomonbrys.kodein.instance
import org.minicluster.Injector
import org.minicluster.helpers.kafka.EasyKafkaConsumer
import org.minicluster.helpers.kafka.KafkaHelper

fun main(args: Array<String>) {

    val injector = Injector()
    val kafkaHelper = KafkaHelper(injector.kodein)
    println(kafkaHelper.createTopics("t1", "t2", "t3", "t4", "t5"))
    val easyKafkaConsumer = injector.kodein.instance<EasyKafkaConsumer>()
    easyKafkaConsumer.subscribe(listOf("t1", "t2", "t3", "t4", "t5"))

    easyKafkaConsumer.runConsumer()

}