package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.ext.ReflectionExtensions.classGettersByName
import ru.adavliatov.atomy.common.ui.api.ext.ReflectionExtensions.propertyValueByName
import kotlin.reflect.KClass

class PropertyExtractor<V>(klass: KClass<*>) {
  private val gettersByName = classGettersByName(klass)

  fun extractProperty(view: V, propertyName: String?, defaultValue: (V) -> Any?) =
    propertyValueByName(view, propertyName, gettersByName)
      ?: defaultValue(view)

  fun extractProperty(view: V, propertyName: String?) =
    if (gettersByName.isNotEmpty()) {
      val getter = gettersByName["createdAt"] ?: gettersByName.values.first()
      extractProperty(view, propertyName) { getter.call(view) }
    } else
      throw IllegalArgumentException("No default value provided")

}
