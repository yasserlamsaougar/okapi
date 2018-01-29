import com.github.salomonbrys.kodein.instance
import org.minicluster.Injector
import org.minicluster.helpers.kafka.EasyKafkaProducer

fun main(args: Array<String>) {

    val injector = Injector()
    val easyKafkaProducer = injector.kodein.instance<EasyKafkaProducer>()

    easyKafkaProducer.produce("t1", "This is a test message")
    easyKafkaProducer.produce("t2", "This is a test message")
    easyKafkaProducer.produce("t3", "This is a test message")
    easyKafkaProducer.produce("t4", "This is a test message")
    easyKafkaProducer.produce("t5", "This is a test message")

}