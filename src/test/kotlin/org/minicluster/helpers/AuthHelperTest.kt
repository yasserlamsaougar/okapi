package org.minicluster.helpers

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.on
import org.minicluster.constants.ServicesConfig
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.env.EnvHelper
import org.minicluster.helpers.kerberos.AuthHelper
import java.nio.file.Paths

object AuthHelperTest : Spek({
    given("an AuthHelper instance") {
        val configHelper = mockk<ConfigHelper>()
        val servicesConfig = mockk<ServicesConfig>()
        val krbConf = Paths.get("/path/to/krb5")
        val jaasConf = Paths.get("/path/to/jaas")
        every { configHelper.servicesConfig } returns servicesConfig
        every { servicesConfig.kerberosConf() } returns krbConf
        every { servicesConfig.jaas() } returns jaasConf

        val envHelper = mockk<EnvHelper>(relaxed = true)

        val kodein = Kodein {
            bind() from instance(configHelper)
            bind() from instance(envHelper)
        }

        on("initialization") {
            val authHelper = AuthHelper(kodein)
            verify(exactly = 1) { envHelper.setJvmProperty("java.security.krb5.conf", krbConf.toString()) }
            verify(exactly = 1) { envHelper.setJvmProperty("java.security.auth.login.config", jaasConf.toString()) }
        }
    }
})