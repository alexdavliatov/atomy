package ru.adavliatov.atomy.common.ui.api.ext

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1.Getter
import kotlin.reflect.full.declaredMemberProperties

object ReflectionExtensions {
  fun classGettersByName(klass: KClass<*>): Map<String, Getter<out Any, Any?>> =
    klass.declaredMemberProperties.associateBy({ it.name }, { it.getter })

  fun <T> propertyValueByName(
    obj: T,
    propertyName: String?,
    properties: Map<String, Getter<out Any, Any?>>
  ) = properties[propertyName]?.call(obj)

  fun <T> projectProperties(
    obj: T,
    fields: Set<String>,
    properties: Map<String, Getter<out Any, Any?>>
  ) = properties
    .filterKeys { fields.contains(it) }
    .map { (name, getter) -> name to getter.call(obj) }
    .toMap()
}
