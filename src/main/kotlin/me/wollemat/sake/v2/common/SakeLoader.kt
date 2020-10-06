package me.wollemat.sake.v2.common

import me.wollemat.sake.v2.desugarer.SakeDesugarer
import me.wollemat.sake.v2.interpreter.SakeInterpreter
import me.wollemat.sake.v2.interpreter.Value
import me.wollemat.sake.v2.parser.SakeStringParser

fun main() {
    println("Hello, World!")
}

fun run(src: String): Value {
    val ast = SakeStringParser(src).parse()
    val dast = SakeDesugarer(ast).desugar()
    // TODO run type checking
    return SakeInterpreter(dast).interp()
}
