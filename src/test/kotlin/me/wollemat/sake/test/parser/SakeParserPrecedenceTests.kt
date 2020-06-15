package me.wollemat.sake.test.parser

class SakeParserPrecedenceTests {
    // @ParameterizedTest
    // @CsvSource(
    //     "*",
    //     "/",
    //     "%",
    //     "+",
    //     "-",
    //     ">=",
    //     ">",
    //     "<=",
    //     "<",
    //     "==",
    //     "!=",
    //     "and",
    //     "or"
    // )
    // fun `grouping is higher than binary operator`(op: String) {
    //     val expr = "x $op (y or z)"
    //     val src = src(expr)
    //
    //     val ast = SakeStringParser(src).parse()
    //
    //     assertEquals(ast(expr(expr)), ast)
    // }
    //
    // @ParameterizedTest
    // @CsvSource(
    //     "-",
    //     "not"
    // )
    // fun `grouping is higher than unary operator`(op: String) {
    //     val expr = "$op (x or y)"
    //     val src = src(expr)
    //
    //     val ast = SakeStringParser(src).parse()
    //
    //     assertEquals(ast(expr(expr)), ast)
    // }
    //
    // @ParameterizedTest
    // @CsvSource(
    //     "+",
    //     "-",
    //     ">=",
    //     ">",
    //     "<=",
    //     "<",
    //     "==",
    //     "!=",
    //     "and",
    //     "or"
    // )
    // fun `multiplication, division and remainder are higher than binary operator`(op: String) {
    //     val exprMult = "x $op y * z"
    //     val exprDiv = "x $op y / z"
    //     val exprRem = "x $op y % z"
    //
    //     val srcMult = src(exprMult)
    //     val srcDiv = src(exprDiv)
    //     val srcRem = src(exprRem)
    //
    //     val astMult = SakeStringParser(srcMult).parse()
    //     val astDiv = SakeStringParser(srcDiv).parse()
    //     val astRem = SakeStringParser(srcRem).parse()
    //
    //     assertEquals(ast(expr(exprMult)), astMult)
    //     assertEquals(ast(expr(exprDiv)), astDiv)
    //     assertEquals(ast(expr(exprRem)), astRem)
    // }
    //
    // @ParameterizedTest
    // @CsvSource(
    //     ">=",
    //     ">",
    //     "<=",
    //     "<",
    //     "==",
    //     "!=",
    //     "and",
    //     "or"
    // )
    // fun `addition and subtraction are higher than binary operator`(op: String) {
    //     val exprAdd = "x $op y + z"
    //     val exprSub = "x $op y - z"
    //
    //     val srcAdd = src(exprAdd)
    //     val srcSub = src(exprSub)
    //
    //     val astAdd = SakeStringParser(srcAdd).parse()
    //     val astSub = SakeStringParser(srcSub).parse()
    //
    //     assertEquals(ast(expr(exprAdd)), astAdd)
    //     assertEquals(ast(expr(exprSub)), astSub)
    // }
    //
    // @ParameterizedTest
    // @CsvSource(
    //     "==",
    //     "!=",
    //     "and",
    //     "or"
    // )
    // fun `ge, gt, le and lt are higher than binary operator`(op: String) {
    //     val exprGe = "x $op y >= z"
    //     val exprGt = "x $op y > z"
    //     val exprLe = "x $op y <= z"
    //     val exprLt = "x $op y < z"
    //
    //     val srcGe = src(exprGe)
    //     val srcGt = src(exprGt)
    //     val srcLe = src(exprLe)
    //     val srcLt = src(exprLt)
    //
    //     val astGe = SakeStringParser(srcGe).parse()
    //     val astGt = SakeStringParser(srcGt).parse()
    //     val astLe = SakeStringParser(srcLe).parse()
    //     val astLt = SakeStringParser(srcLt).parse()
    //
    //     assertEquals(ast(expr(exprGe)), astGe)
    //     assertEquals(ast(expr(exprGt)), astGt)
    //     assertEquals(ast(expr(exprLe)), astLe)
    //     assertEquals(ast(expr(exprLt)), astLt)
    // }
    //
    // @ParameterizedTest
    // @CsvSource(
    //     "and",
    //     "or"
    // )
    // fun `equality and inequality are higher than binary operator`(op: String) {
    //     val exprEq = "x $op y == z"
    //     val exprNeq = "x $op y != z"
    //
    //     val srcEq = src(exprEq)
    //     val srcNeq = src(exprNeq)
    //
    //     val astEq = SakeStringParser(srcEq).parse()
    //     val astNeq = SakeStringParser(srcNeq).parse()
    //
    //     assertEquals(ast(expr(exprEq)), astEq)
    //     assertEquals(ast(expr(exprNeq)), astNeq)
    // }
    //
    // @Test
    // fun `and is higher than binary operator`() {
    //     val expr = "x or y and z"
    //     val src = src(expr)
    //
    //     val ast = SakeStringParser(src).parse()
    //
    //     assertEquals(ast(expr(expr)), ast)
    // }
}
