package org.minicluster

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.Javalin
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.services.Service

class App(val kodein: Kodein) {
    private val configHelper: ConfigHelper = kodein.instance()
    private val listOfServices: List<Service> = kodein.instance()

    fun start() {
        val port = configHelper.servicesConfig.port()
        val app = Javalin.create()
                .port(port).start()
        listOfServices.forEach {
            it.setup(app)
        }
    }
}