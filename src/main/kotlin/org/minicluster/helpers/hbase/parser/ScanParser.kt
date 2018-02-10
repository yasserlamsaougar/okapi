package org.minicluster.helpers.hbase.parser

import com.github.salomonbrys.kodein.Kodein
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.apache.hadoop.hbase.filter.Filter
import org.apache.hadoop.hbase.filter.FilterList
import yla.hbase.filter.FilterLexer
import yla.hbase.filter.FilterParser
import java.util.*

class ScanParser(kodein: Kodein) {

    fun parse(text: String) : Filter {
        val lexer = FilterLexer(CharStreams.fromString(text))
        val tokenStream = CommonTokenStream(lexer)
        val parser = FilterParser(tokenStream)
        val tree = parser.expr()
        val stack = Stack<FilterList>()
        val walker = ParseTreeWalker()
        walker.walk(FilterTreeWalker(stack), tree)
        return stack.pop()
    }
}