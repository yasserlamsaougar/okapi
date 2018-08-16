package org.minicluster

import com.github.salomonbrys.kodein.*
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.env.EnvHelper
import org.minicluster.helpers.hbase.ConnectionPool
import org.minicluster.helpers.hbase.HbaseHelper
import org.minicluster.helpers.hbase.parser.ScanParser
import org.minicluster.helpers.hdfs.HdfsHelper
import org.minicluster.helpers.kafka.EasyKafkaConsumer
import org.minicluster.helpers.kafka.EasyKafkaProducer
import org.minicluster.helpers.kafka.KafkaHelper
import org.minicluster.helpers.kafka.SafeKafkaConsumer
import org.minicluster.helpers.kerberos.AuthHelper
import org.minicluster.services.Service
import org.minicluster.splitters.SimpleSplitter
import org.minicluster.splitters.Splitter
import org.reflections.Reflections
import java.util.stream.Stream


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
        bind() from singleton {
            EasyKafkaConsumer(kodein)
        }
        bind() from singleton {
            SafeKafkaConsumer(kodein)
        }
        bind() from provider {
            ScanParser(kodein)
        }
        bind<Stream<Service>>() with singleton {
            getListOfServices(kodein)
        }
        bind<Splitter>() with singleton {
            SimpleSplitter()
        }
        constant("globalProperties") with System.getProperty("config", "/main.conf")
        constant("propertiesPrefix") with System.getProperty("env", "local")
    }

    fun getListOfServices(kodein: Kodein): Stream<Service> {
        val reflections = Reflections("org.minicluster.services")
        return reflections.getSubTypesOf(Service::class.java).parallelStream().map {
            it.getConstructor(Kodein::class.java).newInstance(kodein)
        }
    }

}