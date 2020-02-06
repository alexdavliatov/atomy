package ru.adavliatov.atomy.common.ui.api.ext

object CollectionExtensions {
  @Suppress("UNCHECKED_CAST")
  fun <T> Collection<T>.sorted(propertyExtractor: (T) -> Any?): List<T> {
    val fieldToItem: Map<Any?, T> = map { propertyExtractor(it) to it }.toMap()
    val fields = fieldToItem
      .keys
      .filterNotNull()
      .takeIf { it.isNotEmpty() }
      ?: return listOf()

    return when (fields.first()) {
      is Comparable<*> -> fields
        .filterIsInstance(Comparable::class.java)
        .sorted { it as Comparable<Comparable<*>> }
      else -> fields
    }
      .asSequence()
      .map { fieldToItem[it] }
      .filterNot { it == null }
      .toList() as List<T>
  }
}