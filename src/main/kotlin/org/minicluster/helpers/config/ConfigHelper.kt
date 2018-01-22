package org.minicluster.helpers.config

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.cfg4k.hocon.HoconConfigLoader
import com.jdiazcano.cfg4k.providers.ProxyConfigProvider
import org.minicluster.constants.ServicesConfig

class ConfigHelper(kodein: Kodein) {

    val servicesConfig: ServicesConfig

    init {
        val loader = HoconConfigLoader(this::class.java.getResource(kodein.instance<String>("globalProperties"))) // Create loader
        val provider = ProxyConfigProvider(loader)                                                      // Create provider
        servicesConfig = provider.bind<ServicesConfig>(prefix = kodein.instance<String>("propertiesPrefix"), type = ServicesConfig::class.java)
    }

}