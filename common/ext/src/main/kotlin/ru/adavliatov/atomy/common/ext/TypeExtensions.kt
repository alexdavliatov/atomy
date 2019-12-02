package ru.adavliatov.atomy.common.ext

import kotlin.reflect.KClass

object TypeExtensions {
  inline fun <reified T> Any.takeAs(): T? = if (this is T) this else null
  @Suppress("UNCHECKED_CAST")
  fun <T : Any> Any.takeAs(klass: KClass<T>): T? = if (klass.isInstance(this)) this as T else null
}
