package org.minicluster.helpers.kerberos

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.apache.hadoop.security.UserGroupInformation
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.env.EnvHelper
import java.nio.file.Path

class AuthHelper(kodein: Kodein) {
    private val configHelper: ConfigHelper = kodein.instance()
    private val envHelper: EnvHelper = kodein.instance()

    init {
        envHelper.setJvmProperty("java.security.krb5.conf", configHelper.servicesConfig.kerberosConf().toString())
        envHelper.setJvmProperty("java.security.auth.login.config", configHelper.servicesConfig.jaas().toString())
    }

    fun authenticate(user: String = configHelper.servicesConfig.keyTabUser(), keytab: Path = configHelper.servicesConfig.keyTab()): UserGroupInformation {
        return UserGroupInformation.loginUserFromKeytabAndReturnUGI(user, keytab.toString())
    }
}