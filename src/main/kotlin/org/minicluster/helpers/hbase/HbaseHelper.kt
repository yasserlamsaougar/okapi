package org.minicluster.helpers.hbase

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.HTableDescriptor
import org.apache.hadoop.hbase.NamespaceDescriptor
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.*
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter
import org.apache.hadoop.hbase.coprocessor.AggregateImplementation
import org.apache.hadoop.hbase.util.Bytes

class HbaseHelper(val kodein: Kodein) {

    val connectionPool: ConnectionPool by kodein.lazy.instance()

    fun createNamespace(namespace: String): Boolean {
        val admin = connectionPool.getConnection().admin
        val hasNamespace = admin.listNamespaceDescriptors().map(NamespaceDescriptor::getName).contains(namespace)
        if (!hasNamespace) {
            admin.createNamespace(NamespaceDescriptor.create(namespace).build())
            return true
        }
        return false
    }

    fun createTable(table: String, colFamilies: String): Boolean {
        val admin = connectionPool.getConnection().admin
        val tableName = TableName.valueOf(table)
        if (!admin.tableExists(tableName)) {
            val tableDescriptor = HTableDescriptor(tableName)
            val colFamiliesSplitted = colFamilies.split(";")
            colFamiliesSplitted.forEach { e ->
                val familyName = HColumnDescriptor(e)
                tableDescriptor.addFamily(familyName)
            }
            tableDescriptor.addCoprocessor(AggregateImplementation::class.java.canonicalName)
            admin.createTable(tableDescriptor)
            return true
        }
        return false
    }

    fun addRows(table: String, data: Map<String, Map<String, String>>): Boolean {
        val admin = connectionPool.getConnection().admin
        val tableName = TableName.valueOf(table)
        if (admin.tableExists(tableName)) {
            val hTable = connectionPool.getConnection().getTable(tableName)
            val puts = data.map {(rowId, value)->
                val put = Put(rowId.bytes())
                value.forEach { (k, v) ->
                    val keySplitted = k.split(":")
                    put.addColumn(keySplitted[0].bytes(), keySplitted[1].bytes(), v.bytes())
                }
                put
            }
            hTable.put(puts)
            hTable.close()
            return true
        }
        return false
    }

    fun deleteRows(table: String, vararg id: String): Boolean {
        val connection = connectionPool.getConnection()
        val admin = connection.admin
        val tableName = TableName.valueOf(table)
        if (admin.tableExists(tableName)) {
            val hTable = connection.getTable(tableName)
            hTable.delete(id.map { Delete(it.bytes()) })
            hTable.close()
            return true
        }
        return false
    }

    fun scanTable(table: String, limit: Int, vararg colFamilies: String): List<SimpleRow>? {
        val connection = connectionPool.getConnection()
        val admin = connection.admin
        val tableName = TableName.valueOf(table)
        if (admin.tableExists(tableName)) {
            val hTable = connection.getTable(tableName)
            val scan = createScan(*colFamilies)
            scan.cacheBlocks = false
            val result = mutableListOf<SimpleRow>()
            val scanner = hTable.getScanner(scan)
            scanner.take(limit).mapTo(result) { createRow(table = table, result = it) }
            scanner.close()
            hTable.close()
            return result
        }
        return null
    }

    fun getRows(table: String, vararg rowIds: String) : List<SimpleRow>?{
        val connection = connectionPool.getConnection()
        val admin = connection.admin
        val tableName = TableName.valueOf(table)
        if (admin.tableExists(tableName)) {
            val hTable = connection.getTable(tableName)
            val result = hTable.get(rowIds.map { Get(it.bytes()) }).map{createRow(table, it)}
            hTable.close()
            return result
        }
        return null
    }

    fun countRows(table: String, vararg colFamilies: String): Long {
        val connection = connectionPool.getConnection()
        val admin = connection.admin
        val tableName = TableName.valueOf(table)
        if (admin.tableExists(tableName)) {
            val aggregationClient = AggregationClient(connection.configuration)
            val scan = createScan(*colFamilies)
            var result = aggregationClient.rowCount(tableName, LongColumnInterpreter(), scan)
            aggregationClient.close()
            return result
        }
        return -1L
    }


    private fun createScan(vararg colFamilies: String): Scan {
        val scan = Scan()
        colFamilies.forEach { scan.addFamily(it.bytes()) }
        return scan
    }

    private fun createRow(table: String, result: Result): SimpleRow {
        val rowId = result.row.toS()
        val columnsByFamily = result.noVersionMap.entries.associate { (family, familyColumns) ->
            val family = family.toS()
            val columns = familyColumns.map { (qualifier, value) ->
                SimpleColumn(family, qualifier = qualifier.toS(), value = value.toS())
            }
            family to columns
        }
        return SimpleRow(table, rowId, columnsByFamily)
    }


    fun String.bytes(): ByteArray {
        return Bytes.toBytes(this)
    }

    fun ByteArray.toS(): String {
        return Bytes.toString(this)
    }

    data class SimpleColumn(val family: String, val qualifier: String, val value: String)
    data class SimpleRow(val tableName: String, val rowId: String, val columns: Map<String, List<SimpleColumn>>)
}