import com.github.salomonbrys.kodein.instance
import org.minicluster.Injector
import org.minicluster.helpers.hbase.HbaseHelper

fun main(args: Array<String>) {
    val hbaseHelper = Injector().kodein.instance<HbaseHelper>()
    hbaseHelper.createTable("test", "D,L")
    for (i in 0..100000) {

        hbaseHelper.addRows("test", hashMapOf(
                "test$i" to hashMapOf(
                        "D:a" to "baaaa$i",
                        "D:b" to "baaaabaaaabaaaabaaaa",
                        "D:c" to "baaaa",
                        "D:d" to "bbaaaabaaaabaaaa",
                        "D:e" to "bbaaaabaaaabaaaa",
                        "D:f" to "bbaaaabaaaabaaaa",
                        "D:g" to "bbaaaabaaaa",
                        "D:m" to "bbaaaabaaaabaaaabaaaa",
                        "D:n" to "bbaaaabaaaabaaaa",
                        "D:k" to "bbaaaabaaaa",
                        "D:z" to "bbaaaabaaaabaaaa",
                        "D:y" to "bbaaaabaaaabaaaa",
                        "D:yyy2" to "bbaaaabaaaabaaaa",
                        "D:yyy3" to "bbaaaabaaaabaaaa",
                        "D:yyy5" to "x",
                        "D:yyy$i" to "x$i",
                        "D:yy" to "bbaaaabaaaabaaaa"
                        )
        ))
    }
    println("looking for line")
    println(hbaseHelper.scanTable("test", 1000, "D", "L", query = "@y == %%x300.+ or %D:a == 'baaaa30'"))
    println(hbaseHelper.countRows("test", "D", "L", query = "@y == %%x300.+ or %D:a == 'baaaa30'"))
}