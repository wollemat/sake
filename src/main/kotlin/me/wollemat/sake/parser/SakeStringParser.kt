package me.wollemat.sake.parser

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

data class SakeStringParser(val src: String) {
    fun parse(): AbstractSyntaxTree {
        val input = ANTLRInputStream(src)
        val lexer = SakeLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = SakeParser(tokens)

        return parser.parse()
    }
}
