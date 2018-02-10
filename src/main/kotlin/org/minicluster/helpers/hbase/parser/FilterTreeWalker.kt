package org.minicluster.helpers.hbase.parser

import org.apache.hadoop.hbase.filter.*
import org.apache.hadoop.hbase.util.Bytes
import yla.hbase.filter.FilterBaseListener
import yla.hbase.filter.FilterParser
import java.util.*

class FilterTreeWalker(private val stack: Stack<FilterList>) : FilterBaseListener() {
    private val operatorMap = hashMapOf(
            "==" to CompareFilter.CompareOp.EQUAL,
            ">" to CompareFilter.CompareOp.GREATER,
            ">=" to CompareFilter.CompareOp.GREATER_OR_EQUAL,
            "<" to CompareFilter.CompareOp.LESS,
            "<=" to CompareFilter.CompareOp.LESS_OR_EQUAL,
            "!=" to CompareFilter.CompareOp.NOT_EQUAL,
            "<>" to CompareFilter.CompareOp.NOT_EQUAL
    )
    private val columnPrefix = "@"
    private val columnValue = "%"
    init {
        if(stack.isEmpty()) {
            stack.push(FilterList())
        }
    }

    override fun enterExpr(ctx: FilterParser.ExprContext) {
        with(ctx) {
            if (K_AND() != null) {
                stack.push(FilterList(FilterList.Operator.MUST_PASS_ALL))
            }
            if (K_OR() != null) {
                stack.push(FilterList(FilterList.Operator.MUST_PASS_ONE))
            }

            if (BIND_PARAMETER() != null && literal_value() != null && operator() != null) {
                val operator = operatorMap[operator().text]
                val columnName = BIND_PARAMETER().text
                val isRegex = literal_value().REGEX_LITERAL() != null
                val literalValue = literal_value().text
                val lastFilterList = stack.peek()
                if (columnName.startsWith(columnValue)) {
                    if (isRegex) {
                        val realColumnValue = columnName.substring(1).split(":")
                        val regexComp = RegexStringComparator(literalValue.substring(2))
                        val filter = SingleColumnValueFilter(
                                Bytes.toBytes(realColumnValue[0]),
                                Bytes.toBytes(realColumnValue[1]),
                                operator,
                                regexComp
                        )
                        lastFilterList.addFilter(filter)
                    } else {
                        val realColumnValue = columnName.substring(1).split(":")
                        val value = literalValue.substring(1, literalValue.length - 1)
                        val filter = SingleColumnValueFilter(
                                Bytes.toBytes(realColumnValue[0]),
                                Bytes.toBytes(realColumnValue[1]),
                                operator,
                                Bytes.toBytes(value)
                        )

                        lastFilterList.addFilter(filter)
                    }
                } else if (columnName.startsWith(columnPrefix)) {
                    if (isRegex) {
                        val realColumnValue = columnName.substring(1)
                        val regex = literalValue.substring(2)
                        val filter = ColumnPrefixFilter(Bytes.toBytes(realColumnValue))
                        val valueFilter = ValueFilter(
                                operator,
                                RegexStringComparator(regex)
                        )
                        val prefixValueFilterList = FilterList()
                        prefixValueFilterList.addFilter(filter)
                        prefixValueFilterList.addFilter(valueFilter)
                        lastFilterList.addFilter(prefixValueFilterList)
                    } else {
                        val realColumnValue = columnName.substring(1)
                        val value = literalValue.substring(1, literalValue.length - 1)
                        val filter = ColumnPrefixFilter(Bytes.toBytes(realColumnValue))
                        val valueFilter = ValueFilter(
                                operator,
                                BinaryComparator(Bytes.toBytes(value))
                        )
                        val prefixValueFilterList = FilterList()
                        prefixValueFilterList.addFilter(filter)
                        prefixValueFilterList.addFilter(valueFilter)
                        lastFilterList.addFilter(prefixValueFilterList)
                    }
                }
            }
        }
    }
    override fun exitExpr(ctx: FilterParser.ExprContext) {
        if (stack.size > 1 && (ctx.K_OR() != null || ctx.K_AND() != null)) {
            val lastFilterList = stack.pop()
            stack.peek().addFilter(lastFilterList)
        }
    }
}