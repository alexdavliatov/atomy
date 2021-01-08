package ru.adavliatov.atomy.toolkit.fuel.service.behaviour

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import ru.adavliatov.atomy.common.service.repo.WithCreateAndHolder
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Unknown
import ru.adavliatov.atomy.common.type.json.WithJsonContext
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.header.Headers.AUTHORIZATION
import ru.adavliatov.atomy.toolkit.fuel.service.WithHolderToHeader
import ru.adavliatov.atomy.toolkit.fuel.service.deserialize.WithFuelIdDeserializer

interface WithFuelCreateAndHolder<Holder : WithHolder, Id : Any, View : Any> :
  WithCreateAndHolder<Holder, Id, View>,
  WithJsonContext,
  WithFuelIdDeserializer<Id>,
  WithHolderToHeader<Holder>,
  WithFuelUrl {

  override fun create(holder: Holder, items: Iterable<View>): Iterable<Id> = url
    .httpPost()
    .jsonBody(context.toJson(items).let { context.asString(it) })
    .header(AUTHORIZATION to holder.toHeader())
    .responseObject(idsDeserializer)
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