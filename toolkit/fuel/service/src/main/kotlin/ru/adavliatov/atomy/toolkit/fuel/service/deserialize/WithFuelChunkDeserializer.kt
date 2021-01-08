package ru.adavliatov.atomy.toolkit.fuel.service.deserialize

import com.github.kittinunf.fuel.core.ResponseDeserializable
import ru.adavliatov.atomy.common.type.chunk.ChunkedData

interface WithFuelChunkDeserializer<View> {
  fun deserializeChunk(content: Content): ChunkedData<View>

  val chunkDeserializer: ResponseDeserializable<ChunkedData<View>>
    get() = object : ResponseDeserializable<ChunkedData<View>> {
      override fun deserialize(content: Content): ChunkedData<View> = deserializeChunk(content)
    }
}