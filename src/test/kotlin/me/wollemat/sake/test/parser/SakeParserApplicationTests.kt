package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SakeParserApplicationTests {
    @ParameterizedTest
    @CsvSource(
        "fail, FailNode"
    )
    fun `no argument builtin function application parsing test`(func: String, node: String) {
        val src = "fun f() -> $func()"

        val ast = SakeStringParser(src).parse()

        Assertions.assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node)])",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "print, PrintNode",
        "head, HeadNode",
        "tail, TailNode"
    )
    fun `single argument builtin function application parsing test`(func: String, node: String) {
        val src = "fun f() -> $func(null)"

        val ast = SakeStringParser(src).parse()

        Assertions.assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=$node(arg=NullNode))])",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "foo",
        "bar",
        "baz"
    )
    fun `no argument user defined function application parsing test`(id: String) {
        val src = "fun f() -> $id()"

        val ast = SakeStringParser(src).parse()

        Assertions.assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=FunctionApplicationNode(id=$id, args=[]))])",
            ast.toString()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "foo : null : NullNode",
        "bar : x : VariableNode(id=x)",
        "baz : null + null : AddNode(expr1=NullNode, expr2=NullNode)", delimiter = ':'
    )
    fun `single argument user defined function application parsing test`(id: String, arg: String, node: String) {
        val src = "fun f() -> $id($arg)"

        val ast = SakeStringParser(src).parse()

        Assertions.assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=FunctionApplicationNode(id=$id, args=[$node]))])",
            ast.toString()
        )
    }

    @Test
    fun `multiple argument user defined function application parsing test`() {
        val src = "fun f() -> foo(1, x, null)"

        val ast = SakeStringParser(src).parse()

        Assertions.assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=f, params=[], expr=FunctionApplicationNode(id=foo, args=[IntegerNode(num=1), VariableNode(id=x), NullNode]))])",
            ast.toString()
        )
    }
}
