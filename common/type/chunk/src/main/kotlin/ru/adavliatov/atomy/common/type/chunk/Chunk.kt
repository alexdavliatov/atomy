package ru.adavliatov.atomy.common.type.chunk

data class Chunk<Item>(val total: Long, val items: List<Item>)