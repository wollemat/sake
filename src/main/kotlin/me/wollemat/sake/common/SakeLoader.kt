package me.wollemat.sake.common

import me.wollemat.sake.parser.SakeLexer
import me.wollemat.sake.parser.SakeParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    val parser = SakeParser(CommonTokenStream(SakeLexer(ANTLRInputStream("fun main() -> print('Hello, World!')"))))
    println(parser.start().function(0).identifier().ID())
}
