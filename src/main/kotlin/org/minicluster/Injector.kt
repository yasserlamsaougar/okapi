package org.minicluster

import com.github.salomonbrys.kodein.*
import org.minicluster.helpers.kerberos.AuthHelper
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.env.EnvHelper
import org.minicluster.helpers.hbase.ConnectionPool
import org.minicluster.helpers.hbase.HbaseHelper
import org.minicluster.helpers.hdfs.HdfsHelper
import org.minicluster.helpers.kafka.EasyKafkaConsumer
import org.minicluster.helpers.kafka.EasyKafkaProducer
import org.minicluster.helpers.kafka.KafkaHelper
import org.minicluster.helpers.kafka.SafeKafkaConsumer
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
        bind() from singleton {
            HbaseHelper(kodein)
        }
        bind() from singleton {
            HdfsHelper(kodein)
        }
        bind() from singleton {
            ConnectionPool(kodein)
        }
        bind() from singleton {
            EasyKafkaProducer(kodein)
        }
        bind () from singleton {
            EasyKafkaConsumer(kodein)
        }
        bind () from singleton {
            SafeKafkaConsumer(kodein)
        }
        constant("globalProperties") with "/main.conf"
        constant("propertiesPrefix") with "main"
    }

}