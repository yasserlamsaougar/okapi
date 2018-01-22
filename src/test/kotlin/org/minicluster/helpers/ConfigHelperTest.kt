package org.minicluster.helpers
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.with
import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.on
import org.minicluster.helpers.config.ConfigHelper
import java.nio.file.Paths

object ConfigHelperTest : Spek({

    given("The configHelper instance without prefix") {
        val kodein = Kodein {
            constant("globalProperties") with "/test-config.conf"
            constant("propertiesPrefix") with ""
        }
        val configHelper = ConfigHelper(kodein)
        on("getting array list property") {
            configHelper.servicesConfig.kafkaBrokers().should.equal(arrayListOf("test1", "test2"))
        }

        on("getting String property") {
            configHelper.servicesConfig.trustStorePassword().should.equal("1234")
        }

        on("getting Path property") {
            configHelper.servicesConfig.trustStore().should.equal(Paths.get("C:/path/to/truststore"))
        }
    }

    given("The configHelper instance with prefix") {
        val kodein = Kodein {
            constant("globalProperties") with "/test-config.conf"
            constant("propertiesPrefix") with "dev"
        }
        val configHelper = ConfigHelper(kodein)
        on("getting array list property") {
            configHelper.servicesConfig.kafkaBrokers().should.equal(arrayListOf("test3", "test4"))
        }

        on("getting String property") {
            configHelper.servicesConfig.trustStorePassword().should.equal("12346")
        }

        on("getting Path property") {
            configHelper.servicesConfig.trustStore().should.equal(Paths.get("C:/path/to/atruststore"))
        }
    }

})