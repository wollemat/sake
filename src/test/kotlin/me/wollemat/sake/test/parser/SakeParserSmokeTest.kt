package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeParserSmokeTest {
    @Test
    fun `hello world smoke test`() {
        val src = """
            fun main() ->
                print('Hello, World!')
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            ast.toString(),
            "AbstractSyntaxTree(funcs=[FunctionNode(id=main, params=[], expr=PrintNode(arg=StringNode(str='Hello, World!')))])"
        )
    }

    @Test
    fun `abs smoke test`() {
        val src = """
            fun abs(x) -> if (x < 0) -x else x
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            ast.toString(),
            "AbstractSyntaxTree(funcs=[FunctionNode(id=abs, params=[x], expr=IfNode(cond=LtNode(expr1=VariableNode(id=x), expr2=IntegerNode(num=0)), expr1=NegNode(expr1=VariableNode(id=x)), expr2=VariableNode(id=x)))])"
        )
    }

    @Test
    fun `fib smoke test`() {
        val src = """
            fun fib(n) ->
                if (n < 0) fail()
                else if (n < 2) n
                     else fib(n - 2) + fib(n - 1)
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            ast.toString(),
            "AbstractSyntaxTree(funcs=[FunctionNode(id=fib, params=[n], expr=IfNode(cond=LtNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=0)), expr1=FailNode, expr2=IfNode(cond=LtNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=2)), expr1=VariableNode(id=n), expr2=AddNode(expr1=FunctionApplicationNode(id=fib, args=[SubNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=2))]), expr2=FunctionApplicationNode(id=fib, args=[SubNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=1))])))))])"
        )
    }

    @Test
    fun `ones smoke test`() {
        val src = """
            fun ones(n) ->
                if (n < 0) fail()
                else if (n == 0) nil
                     else 1 :: ones(n - 1)
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            ast.toString(),
            "AbstractSyntaxTree(funcs=[FunctionNode(id=ones, params=[n], expr=IfNode(cond=LtNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=0)), expr1=FailNode, expr2=IfNode(cond=EqNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=0)), expr1=NilNode, expr2=AppendNode(expr1=IntegerNode(num=1), expr2=FunctionApplicationNode(id=ones, args=[SubNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=1))])))))])"
        )
    }

    @Test
    fun `index smoke test`() {
        val src = """
            fun index(i, li) ->
                if (i < 0) fail()
                else if (i == 0) head(li)
                     else index(i - 1, tail(li))
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            ast.toString(),
        "AbstractSyntaxTree(funcs=[FunctionNode(id=index, params=[i, li], expr=IfNode(cond=LtNode(expr1=VariableNode(id=i), expr2=IntegerNode(num=0)), expr1=FailNode, expr2=IfNode(cond=EqNode(expr1=VariableNode(id=i), expr2=IntegerNode(num=0)), expr1=HeadNode(arg=VariableNode(id=li)), expr2=FunctionApplicationNode(id=index, args=[SubNode(expr1=VariableNode(id=i), expr2=IntegerNode(num=1)), TailNode(arg=VariableNode(id=li))]))))])"
            )
    }

    @Test
    fun `largest smoke test`() {
        val src = """
            fun largest(li) ->
                if (li == nil) fail()
                else if (head(li) > largest(tail(li))) head(li)
                     else largest(tail(li))
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            ast.toString(),
            "AbstractSyntaxTree(funcs=[FunctionNode(id=largest, params=[li], expr=IfNode(cond=EqNode(expr1=VariableNode(id=li), expr2=NilNode), expr1=FailNode, expr2=IfNode(cond=GtNode(expr1=HeadNode(arg=VariableNode(id=li)), expr2=FunctionApplicationNode(id=largest, args=[TailNode(arg=VariableNode(id=li))])), expr1=HeadNode(arg=VariableNode(id=li)), expr2=FunctionApplicationNode(id=largest, args=[TailNode(arg=VariableNode(id=li))]))))])"
        )
    }
}
