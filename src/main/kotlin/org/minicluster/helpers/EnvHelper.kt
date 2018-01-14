package org.minicluster.helpers

import com.github.salomonbrys.kodein.Kodein

class EnvHelper(val kodein: Kodein) {

    fun getJvmProperty(property: String) : String {
        return System.getProperty(property)
    }

    fun getTimeNano() : Long {
        return System.nanoTime()
    }

    fun getTimeMillis() : Long {
        return System.currentTimeMillis()
    }
}