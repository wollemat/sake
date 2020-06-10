package me.wollemat

import me.wollemat.parser.HelloLexer
import me.wollemat.parser.HelloParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    val parser = HelloParser(CommonTokenStream(HelloLexer(ANTLRInputStream("fun main() -> print('Hello, World!')"))))
    println(parser.start().function(0).identifier().ID())
}
