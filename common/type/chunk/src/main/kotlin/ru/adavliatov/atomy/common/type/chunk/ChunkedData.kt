package ru.adavliatov.atomy.common.type.chunk

data class ChunkedData<Item>(
  val total: Long = 0,
  val chunk: Chunk,
  val items: List<Item>
)