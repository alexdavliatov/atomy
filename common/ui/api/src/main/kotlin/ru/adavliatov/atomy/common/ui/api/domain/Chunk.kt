package ru.adavliatov.atomy.common.ui.api.domain

data class Chunk(
  val limit: Int = 20,
  val offset: Int = 0,
  val sortDirection: SortDirection = DESC,
  val properties: Set<String>,
  val sortBy: String? = null,
  val searchBy: String,
  val searchField: String? = null
) {
  fun toPage(): PageRequest = PageRequest.n(limit, offset)
}
