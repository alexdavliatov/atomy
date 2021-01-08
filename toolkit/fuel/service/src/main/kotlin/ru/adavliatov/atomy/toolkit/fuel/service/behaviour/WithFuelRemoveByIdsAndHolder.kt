package ru.adavliatov.atomy.toolkit.fuel.service.behaviour

import com.github.kittinunf.fuel.httpDelete
import ru.adavliatov.atomy.common.service.repo.WithEntityToId
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.service.repo.WithRemoveByIdsAndHolder
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Unknown
import ru.adavliatov.atomy.common.type.json.WithJsonContext
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.header.Headers.AUTHORIZATION
import ru.adavliatov.atomy.toolkit.fuel.service.WithHolderToHeader
import ru.adavliatov.atomy.toolkit.fuel.service.deserialize.WithFuelIdDeserializer

interface WithFuelRemoveByIdsAndHolder<Holder : WithHolder, Id : Any, View : Any> :
  WithRemoveByIdsAndHolder<Holder, Id, View>,
  WithJsonContext,
  WithFuelIdDeserializer<Id>,
  WithHolderToHeader<Holder>,
  WithFuelUrl,
  WithEntityToId<Id, View> {

  override fun removeByIds(holder: Holder, ids: Iterable<Id>) = ids.forEach { id ->
    "$url/${id}"
      .httpDelete()
      .header(AUTHORIZATION to holder.toHeader())
      .response()
      .let { (_, rs, result) ->
        result
          .fold(
            { },
            {
              throw ApiError(rs.statusCode, Unknown, message = it.localizedMessage)
            }
          )
      }
  }
}