package ru.adavliatov.atomy.common.type.name

interface Value<T> {
  val value: T
}

open class ValueHolder<T>(override val value: T): Value<T>

inline class NameValue(val name: String): Value<String> {
  override val value: String
  get() = name
}
data class Name<M>(val name: NameValue)