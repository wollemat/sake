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
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=main, params=[], expr=PrintNode(msg=StringNode(str=Hello, World!)))])",
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
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=abs, params=[x], expr=IfNode(cond=LtNode(expr1=VariableNode(id=x), expr2=IntegerNode(num=0)), exprIf=NegNode(expr=VariableNode(id=x)), exprElif=[], exprElse=VariableNode(id=x)))])",
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
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=fib, params=[n], expr=IfNode(cond=LtNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=0)), exprIf=FailNode(msg=StringNode(str=negative index nt allowed)), exprElif=[ElIfNode(cond=LtNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=2)), expr=VariableNode(id=n))], exprElse=AddNode(expr1=ApplicationNode(id=fib, args=[SubNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=2))]), expr2=ApplicationNode(id=fib, args=[SubNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=1))]))))])",
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
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=ones, params=[n], expr=IfNode(cond=LtNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=0)), exprIf=FailNode(msg=StringNode(str=negative length not allowed)), exprElif=[ElIfNode(cond=EqNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=0)), expr=NilNode)], exprElse=AppendNode(expr1=IntegerNode(num=1), expr2=ApplicationNode(id=ones, args=[SubNode(expr1=VariableNode(id=n), expr2=IntegerNode(num=1))]))))])",
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
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=index, params=[i, li], expr=IfNode(cond=LtNode(expr1=VariableNode(id=i), expr2=IntegerNode(num=0)), exprIf=FailNode(msg=StringNode(str=negative index not allowed)), exprElif=[ElIfNode(cond=EqNode(expr1=VariableNode(id=i), expr2=IntegerNode(num=0)), expr=ApplicationNode(id=head, args=[VariableNode(id=li)]))], exprElse=ApplicationNode(id=index, args=[SubNode(expr1=VariableNode(id=i), expr2=IntegerNode(num=1)), ApplicationNode(id=tail, args=[VariableNode(id=li)])])))])",
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
            "AbstractSyntaxTree(funcs=[FunctionDeclaration(id=largest, params=[li], expr=IfNode(cond=EqNode(expr1=VariableNode(id=li), expr2=NilNode), exprIf=FailNode(msg=StringNode(str=no largest value in empty list)), exprElif=[ElIfNode(cond=GtNode(expr1=ApplicationNode(id=head, args=[VariableNode(id=li)]), expr2=ApplicationNode(id=largest, args=[ApplicationNode(id=tail, args=[VariableNode(id=li)])])), expr=ApplicationNode(id=head, args=[VariableNode(id=li)]))], exprElse=ApplicationNode(id=largest, args=[ApplicationNode(id=tail, args=[VariableNode(id=li)])])))])",
            ast.toString()
        )
    }
}
