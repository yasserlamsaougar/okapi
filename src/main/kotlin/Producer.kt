import com.github.salomonbrys.kodein.instance
import org.minicluster.Injector
import org.minicluster.helpers.kafka.EasyKafkaProducer

fun main(args: Array<String>) {

    val injector = Injector()
    val easyKafkaProducer = injector.kodein.instance<EasyKafkaProducer>()

    easyKafkaProducer.produce("t1", message = "This is a test message")
    easyKafkaProducer.produce("t2", message = "This is a test message")
    easyKafkaProducer.produce("t3", message = "This is a test message")
    easyKafkaProducer.produce("t4", message = "This is a test message")
    easyKafkaProducer.produce("t5", message = "This is a test message")

}