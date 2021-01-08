package ru.adavliatov.atomy.toolkit.fuel.service.behaviour

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPatch
import ru.adavliatov.atomy.common.service.repo.WithEntityToId
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.service.repo.WithModifyAndHolder
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Unknown
import ru.adavliatov.atomy.common.type.json.WithJsonContext
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.header.Headers.AUTHORIZATION
import ru.adavliatov.atomy.toolkit.fuel.service.WithHolderToHeader
import ru.adavliatov.atomy.toolkit.fuel.service.deserialize.WithFuelIdDeserializer

interface WithFuelModifyAndHolder<Holder : WithHolder, Id : Any, View : Any> :
  WithModifyAndHolder<Holder, View>,
  WithJsonContext,
  WithFuelIdDeserializer<Id>,
  WithHolderToHeader<Holder>,
  WithFuelUrl,
  WithEntityToId<Id, View> {

  override fun modify(holder: Holder, item: View): View = "$url/${item.id()}"
    .httpPatch()
    .jsonBody(context.toJson(item).let { context.asString(it) })
    .header(
      AUTHORIZATION to holder.toHeader()
    )
    .response()
    .let { (_, rs, result) ->
      result
        .fold(
          { item },
          {
            throw ApiError(rs.statusCode, Unknown, message = it.localizedMessage)
          }
        )
    }
}