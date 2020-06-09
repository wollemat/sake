package me.wollemat

import me.wollemat.parser.HelloLexer
import me.wollemat.parser.HelloParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    val parser = HelloParser(CommonTokenStream(HelloLexer(ANTLRInputStream("hello hans"))))
    println(parser.start().ID())
}
