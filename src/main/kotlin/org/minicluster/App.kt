package org.minicluster

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.minicluster.helpers.config.ConfigHelper

class App(val kodein: Kodein) {
    val configHelper: ConfigHelper = kodein.instance()

    fun start() {

    }
}