package me.wollemat.sake.test.parser

import me.wollemat.sake.parser.SakeStringParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SakeParserSmokeTests {
    @Test
    fun `hello world smoke test`() {
        val src = """
            fun main() ->
                print('Hello, World!')
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=main, params=[], expr=PrintNode(msg=StringNode(str=Hello, World!))$END",
            ast.toString()
        )
    }

    @Test
    fun `abs function smoke test`() {
        val src = """
            fun abs(x) -> if (x < 0) -x else x
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=abs, params=[x], expr=IfNode(cond=LtNode(left=VariableNode(id=x), right=IntegerNode(num=0)), left=NegNode(expr=VariableNode(id=x)), right=VariableNode(id=x))$END",
            ast.toString()
        )
    }

    @Test
    fun `fib function smoke test`() {
        val src = """
            fun fib(n) ->
                if (n < 0) fail('negative index nt allowed')
                elif (n < 2) n
                else fib(n - 2) + fib(n - 1)
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=fib, params=[n], expr=IfNode(cond=LtNode(left=VariableNode(id=n), right=IntegerNode(num=0)), left=FailNode(msg=StringNode(str=negative index nt allowed)), right=IfNode(cond=LtNode(left=VariableNode(id=n), right=IntegerNode(num=2)), left=VariableNode(id=n), right=AddNode(left=ApplicationNode(id=fib, args=[SubNode(left=VariableNode(id=n), right=IntegerNode(num=2))]), right=ApplicationNode(id=fib, args=[SubNode(left=VariableNode(id=n), right=IntegerNode(num=1))]))))$END",
            ast.toString()
        )
    }

    @Test
    fun `ones function smoke test`() {
        val src = """
            fun ones(n) ->
                if (n < 0) fail('negative length not allowed')
                elif (n == 0) nil
                else 1 :: ones(n - 1)
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=ones, params=[n], expr=IfNode(cond=LtNode(left=VariableNode(id=n), right=IntegerNode(num=0)), left=FailNode(msg=StringNode(str=negative length not allowed)), right=IfNode(cond=EqNode(left=VariableNode(id=n), right=IntegerNode(num=0)), left=NilNode, right=AppendNode(head=IntegerNode(num=1), tail=ApplicationNode(id=ones, args=[SubNode(left=VariableNode(id=n), right=IntegerNode(num=1))]))))$END",
            ast.toString()
        )
    }

    @Test
    fun `index function smoke test`() {
        val src = """
            fun index(i, li) ->
                if (i < 0) fail('negative index not allowed')
                elif (i == 0) head(li)
                else index(i - 1, tail(li))
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=index, params=[i, li], expr=IfNode(cond=LtNode(left=VariableNode(id=i), right=IntegerNode(num=0)), left=FailNode(msg=StringNode(str=negative index not allowed)), right=IfNode(cond=EqNode(left=VariableNode(id=i), right=IntegerNode(num=0)), left=ApplicationNode(id=head, args=[VariableNode(id=li)]), right=ApplicationNode(id=index, args=[SubNode(left=VariableNode(id=i), right=IntegerNode(num=1)), ApplicationNode(id=tail, args=[VariableNode(id=li)])])))$END",
            ast.toString()
        )
    }

    @Test
    fun `largest function smoke test`() {
        val src = """
            fun largest(li) ->
                if (li == nil) fail('no largest value in empty list')
                elif (head(li) > largest(tail(li))) head(li)
                else largest(tail(li))
        """.trimIndent()

        val ast = SakeStringParser(src).parse()

        assertEquals(
            "AbstractSyntaxTree(funcs=[FunctionNode(id=largest, params=[li], expr=IfNode(cond=EqNode(left=VariableNode(id=li), right=NilNode), left=FailNode(msg=StringNode(str=no largest value in empty list)), right=IfNode(cond=GtNode(left=ApplicationNode(id=head, args=[VariableNode(id=li)]), right=ApplicationNode(id=largest, args=[ApplicationNode(id=tail, args=[VariableNode(id=li)])])), left=ApplicationNode(id=head, args=[VariableNode(id=li)]), right=ApplicationNode(id=largest, args=[ApplicationNode(id=tail, args=[VariableNode(id=li)])])))$END",
            ast.toString()
        )
    }
}
