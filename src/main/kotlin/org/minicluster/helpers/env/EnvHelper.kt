package org.minicluster.helpers.env

import com.github.salomonbrys.kodein.Kodein

class EnvHelper(val kodein: Kodein) {

    fun getJvmProperty(property: String) : String {
        return System.getProperty(property)
    }

    fun setJvmProperty(property: String, value: String) {
        System.setProperty(property, value)
    }
}