package ru.adavliatov.atomy.toolkit.fuel.service.behaviour

import com.github.kittinunf.fuel.httpGet
import ru.adavliatov.atomy.common.service.repo.WithChunkedAndHolder
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Unknown
import ru.adavliatov.atomy.common.type.json.WithJsonContext
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.header.Headers.AUTHORIZATION
import ru.adavliatov.atomy.toolkit.fuel.service.WithHolderToHeader
import ru.adavliatov.atomy.toolkit.fuel.service.deserialize.WithFuelChunkDeserializer

interface WithFuelChunkedAndHolder<Holder : WithHolder, View : Any> :
  WithChunkedAndHolder<Holder, View>,
  WithJsonContext,
  WithFuelChunkDeserializer<View>,
  WithHolderToHeader<Holder>,
  WithFuelUrl {

  override fun findChunked(holder: Holder, chunk: Chunk): ChunkedData<View> = url
    .httpGet(
      listOf(
        "limit" to chunk.limit,
        "offset" to chunk.offset
      )
    )
    .header(
      AUTHORIZATION to holder.toHeader()
    )
    .responseObject(chunkDeserializer)
    .let { (_, rs, result) ->
      result
        .fold(
          { it },
          {
            throw ApiError(rs.statusCode, Unknown, message = it.localizedMessage)
          }
        )
    }
}