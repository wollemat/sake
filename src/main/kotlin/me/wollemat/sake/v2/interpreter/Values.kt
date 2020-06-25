package me.wollemat.sake.v2.interpreter

sealed class Value

data class NumValue(val num: Double) : Value()
data class BoolValue(val bool: Boolean) : Value()
data class StringValue(val str: String) : Value()
data class ListValue(val values: List<Value>) : Value()
