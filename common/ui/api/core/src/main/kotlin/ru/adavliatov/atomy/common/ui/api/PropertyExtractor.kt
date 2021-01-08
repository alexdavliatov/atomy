package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.ext.ReflectionExtensions.classGettersByName
import ru.adavliatov.atomy.common.ui.api.ext.ReflectionExtensions.propertyValueByName
import kotlin.reflect.KClass

class PropertyExtractor<V>(klass: KClass<*>) {
  private val gettersByName = classGettersByName(klass)
  private val defaultGetter = gettersByName["createdAt"]
    ?: gettersByName["name"]
    ?: gettersByName["id"]
    ?: gettersByName.values.firstOrNull()

  fun extractProperty(view: V, propertyName: String?, defaultValue: (V) -> Any?) =
    propertyValueByName(view, propertyName, gettersByName)
      ?: defaultValue(view)

  fun extractProperty(view: V, propertyName: String?) = defaultGetter
    ?.let { extractProperty(view, propertyName) { defaultGetter.call(view) } }
    ?: throw IllegalArgumentException("No default value provided")

}
