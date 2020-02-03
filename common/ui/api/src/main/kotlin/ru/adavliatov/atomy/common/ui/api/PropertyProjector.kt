package ru.yandex.contest.web.api.priv.admin.v1

import ru.yandex.contest.extension.classGettersByName
import ru.yandex.contest.extension.projectProperties
import kotlin.reflect.KClass

class PropertyProjector<V>(val klass: KClass<*>) {
  private val gettersByName = classGettersByName(klass)

  fun project(view: V, properties: Set<String>? = setOf()): Any? =
    if (properties == null || properties.isEmpty()) view else projectProperties(view, properties, gettersByName)
}
