package ru.adavliatov.atomy.common.type.name

interface Value<T> {
  val value: T
}

open class ValueHolder<T>(override val value: T) : Value<T>

@JvmInline
value class StringValue(override val value: String) : Value<String>

@JvmInline
value class NameValue(val name: String) : Value<String> {
  override val value: String
    get() = name
}

data class Name<M>(val name: NameValue)