import com.github.salomonbrys.kodein.instance
import org.minicluster.Injector
import org.minicluster.helpers.kafka.EasyKafkaProducer
import java.util.*

fun main(args: Array<String>) {

    val injector = Injector()
    val easyKafkaProducer = injector.kodein.instance<EasyKafkaProducer>()

    val random = Random()
    (0 until 1000000).forEach{
        if(random.nextBoolean()) {

            easyKafkaProducer.produce("test", message = "This is a true message")
        }
        else {
            easyKafkaProducer.produce("test", message = "This is a false message")
        }
    }

}