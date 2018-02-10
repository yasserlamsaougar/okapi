package org.minicluster.services

import com.github.salomonbrys.kodein.Kodein
import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.path
import io.javalin.Javalin

class KafkaService(val kodein: Kodein) : Service {

    override fun setup(app: Javalin) {
        val ns = "kafka"
        app.get("/$ns/hello") {
            it.result("hello $ns")
        }
    }
}