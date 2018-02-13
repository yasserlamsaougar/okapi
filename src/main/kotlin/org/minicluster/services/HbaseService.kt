package org.minicluster.services

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.ApiBuilder.*
import io.javalin.Context
import io.javalin.Javalin
import org.apache.http.HttpStatus
import org.minicluster.helpers.hbase.HbaseHelper
import org.minicluster.splitters.Splitter
import org.minicluster.splitters.tasks.HbaseSplittableTask


class HbaseService(val kodein: Kodein) : Service {

    private val hbaseHelper: HbaseHelper = kodein.instance()
    private val splitter: Splitter = kodein.instance()
    private val defaultLimit = 10

    override fun setup(app: Javalin) {
        app.routes {
            path("hbase") {
                path("table") {
                    post(this::createTable)
                    get(this::listTables)
                    path("delete") {
                        path("rows") {
                            delete("/:namespace/:name", this::deleteRows)
                        }
                        delete("/:namespace/:name", this::deleteTable)
                        delete("/:name", this::deleteTable)
                    }
                    path("get") {
                        get("/:namespace/:name/", this::getTableRows)
                        get("/:name/", this::getTableRows)
                    }
                    path("scan") {
                        get("/:namespace/:name/", this::scanTable)
                        get("/:name/", this::scanTable)
                    }
                    path("count") {
                        get("/:namespace/:name/", this::countTable)
                        get("/:name/", this::countTable)
                    }
                    post("/put", this::putData)
                }
            }
        }
    }

    fun createTable(ctx: Context) {
        val table = ctx.bodyAsClass(Table::class.java)
        if (table.namespace.isNotEmpty()) {
            hbaseHelper.createNamespace(table.namespace)
        }
        val created = hbaseHelper.createTable(table = getTableName(table.namespace, table.name), colFamilies = table.cfs)
        if (created) {
            ctx.status(HttpStatus.SC_CREATED)
        } else {
            ctx.status(HttpStatus.SC_CONFLICT)
        }
        ctx.json(table)
    }

    fun listTables(ctx: Context) {
        ctx.json(hbaseHelper.listTables())
    }

    fun deleteTable(ctx: Context) {
        val namespace = ctx.param("namespace") ?: "default"
        val name = ctx.param("name")
        val deleted = hbaseHelper.deleteTable(getTableName(namespace, name))
        val response = mapOf(
                "namespace" to namespace,
                "name" to name
        )
        if (deleted) {
            ctx.status(HttpStatus.SC_OK)
        } else {
            ctx.status(HttpStatus.SC_NOT_FOUND)
        }
        ctx.json(response)
    }

    fun deleteRows(ctx: Context) {
        val namespace = ctx.param("namespace") ?: "default"
        val name = ctx.param("name")
        val ids = ctx.queryParams("rowId") ?: emptyArray()
        val deleted = hbaseHelper.deleteRows(getTableName(namespace, name), *ids)
        if (deleted) {
            ctx.status(HttpStatus.SC_OK)
        } else {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "table $namespace:$name doesn't exist"
            ))
        }
    }

    fun scanTable(ctx: Context) {
        val namespace = ctx.param("namespace") ?: "default"
        val name = ctx.param("name")
        val query = ctx.queryParam("query").orEmpty()
        val cfs = ctx.queryParams("cf").orEmpty()
        val limit = ctx.queryParam("limit")?.toInt() ?: defaultLimit
        val hbaseSplittableTask: Map<String, Any?> = mapOf(
                "name" to name,
                "namespace" to namespace
        )
        if (hbaseHelper.tableExists(table = getTableName(namespace, name))) {
            val results: List<HbaseHelper.SimpleRow> = splitter.split(HbaseSplittableTask(hbaseSplittableTask, kodein)) {
                val startKey = it.arguments["startKey"] as ByteArray
                val endKey = it.arguments["endKey"] as ByteArray
                hbaseHelper.scanTable(table = getTableName(namespace, name), startRow = startKey, endRow = endKey, limit = limit, colFamilies = *cfs, query = query)!!
            }
            ctx.json(mapOf(
                    "count" to results.size,
                    "rows" to results
            ))
        } else {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "table $namespace:$name doesn't exist"
            ))
        }


    }

    fun countTable(ctx: Context) {
        val namespace = ctx.param("namespace") ?: "default"
        val name = ctx.param("name")
        val query = ctx.queryParam("query").orEmpty()
        val cfs = ctx.queryParams("cf").orEmpty()
        val results = hbaseHelper.countRows(table = getTableName(namespace, name), colFamilies = *cfs, query = query)
        if (results == -1L) {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "table $namespace:$name doesn't exist"
            ))
        } else {
            ctx.json(mapOf(
                    "namespace" to namespace,
                    "name" to name,
                    "cfs" to cfs,
                    "count" to results
            ))
        }
    }

    fun getTableRows(ctx: Context) {
        val namespace = ctx.param("namespace") ?: "default"
        val name = ctx.param("name")
        val ids = ctx.queryParams("rowId") ?: emptyArray()
        val results = hbaseHelper.getRows(getTableName(namespace, name), *ids)
        if (results == null) {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "table $namespace:$name doesn't exist"
            ))
        } else {
            ctx.json(results)
        }
    }

    fun putData(ctx: Context) {
        val data = ctx.bodyAsClass(HData::class.java)
        val added = hbaseHelper.addRows(getTableName(data.namespace, data.name), data.rows)
        if (added) {
            ctx.status(HttpStatus.SC_OK)
            ctx.json(mapOf(
                    "message" to "added or updated ${data.rows.size} rows"
            ))
        } else {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "table ${data.namespace}:${data.name} doesn't exist"
            ))
        }
    }

    private fun getTableName(namespace: String, name: String?) = arrayOf(namespace, name).joinToString(":")

    data class Table(val namespace: String = "", val name: String, val cfs: String)
    data class HData(val namespace: String = "default", val name: String, val rows: Map<String, Map<String, String>>)
}