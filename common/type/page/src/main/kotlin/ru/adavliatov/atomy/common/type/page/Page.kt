package ru.adavliatov.atomy.common.type.page

import ru.adavliatov.atomy.common.type.page.SortDirection.DESC

data class Page(
  val limit: Int = 50,
  val offset: Int = 0,
  val sortDirection: SortDirection = DESC,
  val properties: Set<String>,
  val sortBy: String? = null,
  val searchBy: String,
  val searchField: String? = null
)