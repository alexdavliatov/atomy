package ru.yandex.contest.web.api.priv.admin.v1

import ru.yandex.contest.domain.ObjectWithId
import ru.yandex.contest.extension.classGettersByName
import ru.yandex.contest.extension.propertyValueByName
import kotlin.reflect.KClass

class PropertyExtractor<V>(val klass: KClass<*>) {
  private val gettersByName = classGettersByName(klass)

  fun extractProperty(view: V, propertyName: String?, defaultValue: (V) -> Any?) =
    propertyValueByName(view, propertyName, gettersByName)
      ?: defaultValue(view)

  fun extractProperty(view: V, propertyName: String?) =
    if (view is ObjectWithId<*>)
      extractProperty(view, propertyName) { view.id }
    else
      throw IllegalArgumentException("No default value provided")
}
