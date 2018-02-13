package org.minicluster.splitters.tasks

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.apache.hadoop.hbase.util.Bytes
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.hbase.HbaseHelper

class HbaseSplittableTask(override val arguments: Map<String, Any?>, override val kodein: Kodein) : SplittableTask<List<HbaseHelper.SimpleRow>> {
    val configHelper: ConfigHelper = kodein.instance()
    val hbaseHelper: HbaseHelper = kodein.instance()

    override fun split(): List<SplittableTask<List<HbaseHelper.SimpleRow>>> {
        val table = arguments["name"].toString()
        val namespace = arguments["namespace"].toString()
        val regionList = hbaseHelper.getRegions(getTableName(namespace, table))
        return regionList!!.map {
            println(Bytes.toString(it.startKey))
            println(Bytes.toString(it.endKey))
            HbaseSplittableTask(mapOf(
                    "startKey" to it.startKey,
                    "endKey" to it.endKey

            ), kodein)
        }
    }

    override fun merge(tasksResults: List<List<HbaseHelper.SimpleRow>>): List<HbaseHelper.SimpleRow> {
        return tasksResults.flatten()
    }

    private fun getTableName(namespace: String, name: String?) = arrayOf(namespace, name).joinToString(":")

}