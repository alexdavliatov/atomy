package ru.adavliatov.atomy.common.type.chunk

data class ChunkedData<Item>(
  val total: Long = 0,
  val chunk: Chunk = Chunk(0, 0),
  val items: List<Item> = listOf()
) {

  companion object {
    fun <Item> chunkOf() = ChunkedData<Item>(0, Chunk(0, 0), listOf())

    fun <Item> Chunk.withItems(items: List<Item>, total: Long = items.size.toLong()) =
      ChunkedData(total, this, items)
  }
}