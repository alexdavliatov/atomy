package ru.adavliatov.atomy.common.ext

import kotlin.reflect.KClass

object TypeExtensions {
  inline fun <reified T> Any.takeAs(): T? = if (this is T) this else null
  inline fun <reified T> Any.takeAs(error: () -> RuntimeException): T = takeAs<T>() ?: throw error()
  @Suppress("UNCHECKED_CAST")
  fun <T : Any> Any.takeAs(klass: KClass<T>): T? = if (klass.isInstance(this)) this as T else null

  fun <T : Any> Any.takeAs(klass: KClass<T>, error: () -> RuntimeException): T? = takeAs(klass) ?: throw error()
}
