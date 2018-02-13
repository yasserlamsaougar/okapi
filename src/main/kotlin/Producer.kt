import com.github.salomonbrys.kodein.instance
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.minicluster.Injector
import org.minicluster.helpers.hbase.HbaseHelper
import kotlin.math.roundToInt

fun main(args: Array<String>) = runBlocking {

    //    val injector = Injector()
//    val easyKafkaProducer = injector.kodein.instance<EasyKafkaProducer>()
//
//    val random = Random()
//    while (true) {
//        async {
//            if (random.nextBoolean()) {
//                easyKafkaProducer.produce("test", message = "This is a true message")
//            } else {
//                easyKafkaProducer.produce("test", message = "This is a false message")
//            }
//        }
//
//    }
    val hbaseHelper = Injector().kodein.instance<HbaseHelper>()
    while (true) {
        async {
            val i = (Math.random() * 10000000).roundToInt()
            hbaseHelper.addRows("yla:test", hashMapOf(
                    "test$i" to hashMapOf(
                            "d:a" to "baaaa$i",
                            "d:b" to "baaaabaaaabaaaabaaaa",
                            "d:c" to "baaaa",
                            "d:d" to "bbaaaabaaaabaaaa",
                            "d:e" to "bbaaaabaaaabaaaa",
                            "d:f" to "bbaaaabaaaabaaaa",
                            "d:g" to "bbaaaabaaaa",
                            "d:m" to "bbaaaabaaaabaaaabaaaa",
                            "d:n" to "bbaaaabaaaabaaaa",
                            "d:k" to "bbaaaabaaaa",
                            "d:z" to "bbaaaabaaaabaaaa",
                            "d:y" to "bbaaaabaaaabaaaa",
                            "d:yyy2" to "bbaaaabaaaabaaaa",
                            "d:yyy3" to "bbaaaabaaaabaaaa",
                            "d:yyy5" to "x",
                            "d:yyy$i" to "x$i",
                            "d:yy" to "bbaaaabaaaabaaaa"
                    )
            ))
        }
    }


}