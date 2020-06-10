
```
fun main() -> print('Hello, World!')

fun abs(x) -> if (x < 0) -x else x

fun fib(n) ->
    if (n < 0) fail()
    else if (n < 2) n
         else fib(n - 2) + fib(n - 1)

fun index(i, li) ->
    if (i < 0) fail()
    else if (i == 0) head(li)
         else index(i - 1, tail(li))

fun ones(n) ->
    if (n < 0) fail()
    else if (n == 0) nil
         else 1 :: ones(n - 1)

fun largest(li) ->
    if (li == nil) fail()
    else if (head(li) > largest(tail(li))) head(li)
         else largest(tail(li))
```
