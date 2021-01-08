package ru.adavliatov.atomy.common.type.chunk.ext

import ru.adavliatov.atomy.common.type.chunk.ChunkedData

object ChunkExtensions {
  fun <T, R> ChunkedData<T>.map(transform: (T) -> R): ChunkedData<R> = ChunkedData(total, chunk, items.map(transform))
}