package ru.adavliatov.atomy.common.type.page.ext

import ru.adavliatov.atomy.common.type.page.*
import ru.adavliatov.atomy.common.type.page.SortDirection.*

object CollectionExtensions {
  fun <T> Iterable<T>.reversed(page: Page): List<T> = if (ASC == page.sortDirection) toList() else reversed()
}