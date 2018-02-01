import com.github.salomonbrys.kodein.instance
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.reactive.consumeEach
import kotlinx.coroutines.experimental.reactive.openSubscription
import kotlinx.coroutines.experimental.runBlocking
import org.minicluster.Injector
import org.minicluster.helpers.kafka.EasyKafkaConsumer
import org.minicluster.helpers.kafka.EasyKafkaProducer
import org.minicluster.helpers.kafka.KafkaHelper

fun main(args: Array<String>) {

    val injector = Injector()
    val kafkaHelper = KafkaHelper(injector.kodein)
    println(kafkaHelper.createTopics("t1", "t2", "t3", "t4", "t5"))
    val easyKafkaConsumer = injector.kodein.instance<EasyKafkaConsumer>()
    val easyKafkaProducer = injector.kodein.instance<EasyKafkaProducer>()

    easyKafkaConsumer.subscribe(listOf("t1", "t2", "t3", "t4", "t5"))

    val source = easyKafkaConsumer.runConsumer()
    source.observeOn(Schedulers.computation())
            .subscribe {
                println(it)
            }
    runBlocking {
        var i = 0
        while (i++ < 10) {
            delay(1000)
            easyKafkaProducer.produce("t1", "t2", message = "${System.currentTimeMillis()}")
        }
    }

}