package org.minicluster.helpers.hbase

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import kotlinx.coroutines.experimental.runBlocking
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HConstants
import org.apache.hadoop.hbase.client.Connection
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.kerberos.AuthHelper
import java.util.concurrent.atomic.AtomicInteger

class ConnectionPool(val kodein: Kodein) {
    private val hbaseConfiguration: Configuration = Configuration(true)
    private val configHelper: ConfigHelper = kodein.instance()
    private val authHelper: AuthHelper = kodein.instance()
    private val connections:MutableList<Connection>
    private val connectionPoolSize = configHelper.servicesConfig.hbasePoolSize()
    private val connectionIndex = AtomicInteger(0)
    init {
        hbaseConfiguration.addResource(Path(configHelper.servicesConfig.hbaseSite().toUri()))
        hbaseConfiguration.setInt(HConstants.HBASE_CLIENT_RETRIES_NUMBER, 3)
        connections = (0 until connectionPoolSize).map {
            createConnection()
        }.toMutableList()

    }

    fun getConnection() : Connection {
        val index = connectionIndex.getAndUpdate {
            (it + 1) % connectionPoolSize
        }
        return runBlocking {
            val connection = connections[index]
            if(connection.isClosed) {
                connections[index] = createConnection()
            }
            return@runBlocking connections[index]
        }
    }

    private fun createConnection() : Connection {
        authHelper.authenticate()
        return ConnectionFactory.createConnection(hbaseConfiguration)
    }

}