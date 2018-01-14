package org.minicluster
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import org.minicluster.helpers.AuthHelper
import org.minicluster.helpers.ConfigHelper
import org.minicluster.helpers.EnvHelper
import org.minicluster.helpers.KafkaHelper
import org.minicluster.services.KafkaService


class Injector {
    val kodein = Kodein {
        bind() from singleton {
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
    }

}