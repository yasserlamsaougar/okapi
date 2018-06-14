package org.minicluster.constants

import java.nio.file.Path


interface ServicesConfig {
    fun kafkaBrokers(): List<String>
    fun trustStore(): Path
    fun trustStorePassword(): String
    fun jaas(): Path
    fun zookeeperConnectionString(): String
    fun hbaseSite(): Path
    fun coreSite(): Path
    fun yarnSite(): Path
    fun hdfsSite(): Path
    fun kafkaSite(): Path
    fun kerberosConf(): Path
    fun keyTabUser(): String
    fun keyTab(): String
    fun hbasePoolSize(): Int = 1
    fun hbaseRetry(): Int = 1
    fun consumerPoolSize(): Int = 1

    fun port(): Int = 7000
}