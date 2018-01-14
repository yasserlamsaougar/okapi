package org.minicluster.helpers

import com.github.salomonbrys.kodein.Kodein
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class ConfigHelper(val kodein: Kodein) {

    val properties: Properties = Properties()
    init {
        properties.load(Files.newInputStream(Paths.get("")))
    }

}