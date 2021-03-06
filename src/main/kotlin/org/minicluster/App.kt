package org.minicluster

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.Javalin
import io.javalin.translator.json.JavalinJacksonPlugin
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.services.Service
import java.util.stream.Stream

class App(val kodein: Kodein) {
    private val configHelper: ConfigHelper = kodein.instance()
    private val listOfServices: Stream<Service> = kodein.instance()
    private val mapper = jacksonObjectMapper()
    fun start() {
        JavalinJacksonPlugin.configure(mapper)
        val port = configHelper.servicesConfig.port()
        val app = Javalin.create()
                .enableDynamicGzip()
                .port(port).start()
        listOfServices.forEach{
            it.setup(app)
        }
    }
}