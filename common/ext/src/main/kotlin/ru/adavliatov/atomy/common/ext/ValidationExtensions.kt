package ru.adavliatov.atomy.common.ext

import org.jetbrains.annotations.Contract
import ru.adavliatov.atomy.common.ext.TypeExtensions.takeAs
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.reflect.KClass

object ValidationExtensions {
  fun validate(test: Boolean, body: () -> RuntimeException) {
    if (!test) throw body.invoke()
  }

  fun validateEmptyCollection(collection: Collection<Any>?, body: () -> RuntimeException) =
    validate(collection.isNullOrEmpty(), body)

  fun <T> T.validate(predicate: (T) -> Boolean, error: RuntimeException): T = apply {
    if (!predicate(this)) throw error
  }

  fun <T> T.validate(predicate: (T) -> Boolean, exceptionProducer: (T) -> RuntimeException): T =
    validate(predicate, exceptionProducer(this))

  fun validateNot(test: Boolean, body: () -> RuntimeException) = validate(!test, body)

  fun validateNotEmptyCollection(collection: Collection<Any>?, body: () -> RuntimeException) =
    validate(collection != null && collection.isNotEmpty(), body)

  inline fun <reified T> Any.validateType(exceptionProducer: () -> RuntimeException) = takeAs<T>()
    ?: throw exceptionProducer()

  fun <T : Any> Any.validateType(klass: KClass<T>, exceptionProducer: (KClass<T>) -> RuntimeException) = takeAs(klass)
    ?: throw exceptionProducer(klass)

  inline fun <reified Expected, reified Actual> Any.validateType(exceptionProducer: (KClass<*>, KClass<*>) -> RuntimeException) =
    takeAs<Expected>()
      ?: throw exceptionProducer(Expected::class, Actual::class)

  inline fun <reified T : Error> Error.validateType() = takeAs<T>() ?: throw this

  inline fun <reified E> assertThrows(invoker: () -> Unit, errorMatcher: E.() -> Unit) {
    try {
      invoker.invoke()
    } catch (error: Throwable) {
      assert(error is E) { "Expected ${E::class} error, got ${error::class}" }
      errorMatcher(error as E)

      return
    }
    throw IllegalStateException("Error ${E::class} not thrown")
  }

}

@OptIn(ExperimentalContracts::class)
@Contract("null -> fail; _ -> param1")
inline fun <T> validateNotNull(model: T?, body: () -> RuntimeException) {
  contract {
    returns() implies (model != null)
  }
  if (model != null) return
  throw body.invoke()
}

@OptIn(ExperimentalContracts::class)
fun validateNotBlank(text: String?, body: () -> RuntimeException) {
  contract {
    returns() implies (text != null)
  }
  if (text != null && text.isNotBlank()) return

  throw body.invoke()
}
