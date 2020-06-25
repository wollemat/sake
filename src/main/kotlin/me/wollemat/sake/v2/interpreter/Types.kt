package me.wollemat.sake.v2.interpreter

sealed class Type

object NumType : Type()
object BoolType : Type()
object StringType : Type()
data class ListType(val type: Type) : Type()
