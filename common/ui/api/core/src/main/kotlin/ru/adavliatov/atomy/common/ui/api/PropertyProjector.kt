package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.ext.ReflectionExtensions.classGettersByName
import ru.adavliatov.atomy.common.ui.api.ext.ReflectionExtensions.projectProperties
import kotlin.reflect.KClass

class PropertyProjector<V>(klass: KClass<*>) {
  private val gettersByName = classGettersByName(klass)

  fun project(view: V, properties: Set<String>? = setOf()): Any? =
    if (properties == null || properties.isEmpty()) view else projectProperties(view, properties, gettersByName)
}
