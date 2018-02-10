package org.minicluster.services

import com.github.salomonbrys.kodein.Kodein
import io.javalin.Javalin

class HbaseService(val kodein: Kodein) : Service {

    override fun setup(app: Javalin) {
        val ns = "hbase"
        app.get("$ns/hello") {
            it.result("hello $ns")
        }
    }
}