package org.minicluster

import com.github.salomonbrys.kodein.*
import org.minicluster.helpers.kerberos.AuthHelper
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.env.EnvHelper
import org.minicluster.helpers.kafka.KafkaHelper
import org.minicluster.services.KafkaService


class Injector {
    val kodein = Kodein {
        bind() from eagerSingleton {
            AuthHelper(kodein)
        }
        bind() from singleton {
            ConfigHelper(kodein)
        }
        bind() from singleton {
            KafkaHelper(kodein)
        }
        bind() from singleton {
            KafkaService(kodein)
        }
        bind() from singleton {
            EnvHelper(kodein)
        }
        constant("globalProperties") with "/main.conf"
        constant("propertiesPrefix") with "main"
    }

}