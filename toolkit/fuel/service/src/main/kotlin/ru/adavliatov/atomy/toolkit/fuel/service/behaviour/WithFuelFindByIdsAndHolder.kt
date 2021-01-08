package ru.adavliatov.atomy.toolkit.fuel.service.behaviour

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import ru.adavliatov.atomy.common.service.repo.WithFindByIdsAndHolder
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Unknown
import ru.adavliatov.atomy.common.type.json.WithJsonContext
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.header.Headers.AUTHORIZATION
import ru.adavliatov.atomy.toolkit.fuel.service.WithHolderToHeader
import ru.adavliatov.atomy.toolkit.fuel.service.deserialize.WithFuelChunkDeserializer

interface WithFuelFindByIdsAndHolder<Holder : WithHolder, Id : Any, View : Any> :
  WithFindByIdsAndHolder<Holder, Id, View>,
  WithJsonContext,
  WithFuelChunkDeserializer<View>,
  WithHolderToHeader<Holder>,
  WithFuelUrl {

  override fun findByIds(holder: Holder, ids: Iterable<Id>): Set<View> = "$url/multiple"
    .httpPost()
    .header(
      AUTHORIZATION to holder.toHeader()
    )
    .jsonBody(context.toJson(ids).let { context.asString(it) })
    .responseObject(chunkDeserializer)
    .let { (_, rs, result) ->
      result
        .fold(
          { it },
          {
            throw ApiError(rs.statusCode, Unknown, message = it.localizedMessage)
          }
        )
        .items
        .toSet()
    }
}