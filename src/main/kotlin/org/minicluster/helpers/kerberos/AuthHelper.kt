package org.minicluster.helpers.kerberos

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import kotlinx.coroutines.experimental.runBlocking
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.security.UserGroupInformation
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.env.EnvHelper

class AuthHelper(kodein: Kodein) {
    private val configHelper: ConfigHelper = kodein.instance()
    private val envHelper: EnvHelper = kodein.instance()

    init {
        envHelper.setJvmProperty("java.security.krb5.conf", configHelper.servicesConfig.kerberosConf().toString())
        envHelper.setJvmProperty("java.security.auth.login.config", configHelper.servicesConfig.jaas().toString())
        val configuration = Configuration(false)
        configuration.addResource(Path(configHelper.servicesConfig.coreSite().toUri()))
        setConfiguration(configuration)
    }

    fun setConfiguration(configuration: Configuration) {
        UserGroupInformation.setConfiguration(configuration)
    }

    fun authenticate(user: String = configHelper.servicesConfig.keyTabUser(), keytab: String = configHelper.servicesConfig.keyTab()) {
        runBlocking {
            UserGroupInformation.loginUserFromKeytab(user, keytab)
        }
    }

}