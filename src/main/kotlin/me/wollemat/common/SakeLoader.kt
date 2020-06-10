package me.wollemat.common

import me.wollemat.parser.SakeLexer
import me.wollemat.parser.SakeParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    val parser = SakeParser(CommonTokenStream(SakeLexer(ANTLRInputStream("fun main() -> print('Hello, World!')"))))
    println(parser.start().function(0).identifier().ID())
}
