package org.minicluster

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.Javalin
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.services.KafkaService

class App(val kodein: Kodein) {
    val configHelper: ConfigHelper = kodein.instance()
    val kafkaService: KafkaService = kodein.instance()
    fun start() {
        val port = configHelper.servicesConfig.port()
        val app = Javalin.create()
                .port(port)

    }
}