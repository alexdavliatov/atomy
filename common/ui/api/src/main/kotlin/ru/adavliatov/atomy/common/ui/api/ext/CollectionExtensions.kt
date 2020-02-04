package ru.adavliatov.atomy.common.ui.api.ext

object CollectionExtensions {
  @Suppress("UNCHECKED_CAST")
  fun <T : Any> Collection<T>.sortedBy(propertyExtractor: (T) -> Any?): List<T> {
    val fieldToItem: Map<Any?, T> = map { propertyExtractor(it) to it }.toMap()
    val fields = fieldToItem
      .keys
      .filterNotNull()
      .takeIf { it.isNotEmpty() }
      ?: return listOf()

    return when (fields.first()) {
      is Comparable<*> -> fields
        .filterIsInstance(Comparable::class.java)
        .sortedBy { it as Comparable<Comparable<*>> }
      else -> fields
    }
      .mapNotNull { fieldToItem[it] }
  }
}