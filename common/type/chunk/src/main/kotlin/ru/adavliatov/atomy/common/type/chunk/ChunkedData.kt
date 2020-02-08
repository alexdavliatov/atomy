package ru.adavliatov.atomy.common.type.chunk

data class ChunkedData<Item>(val chunk: Chunk, val items: List<Item>, val total: Long = 0)