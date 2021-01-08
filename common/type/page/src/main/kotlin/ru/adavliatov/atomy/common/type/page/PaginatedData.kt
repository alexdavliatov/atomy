package ru.adavliatov.atomy.common.type.page

data class PaginatedData<Item>(
  val total: Long = 0,
  val chunk: Page,
  val items: List<Item>
) {

  companion object {
    fun <Item> paginatedOf() = PaginatedData<Item>(0, Page(50, 0), listOf())

    fun <Item> Page.withItems(items: List<Item>, total: Long = items.size.toLong()) =
      PaginatedData(total, this, items)
  }
}