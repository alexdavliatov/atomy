package ru.adavliatov.atomy.toolkit.fuel.service.behaviour

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import ru.adavliatov.atomy.common.service.repo.WithFetchOrCreateAndHolder
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Unknown
import ru.adavliatov.atomy.common.type.json.WithJsonContext
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.header.Headers.AUTHORIZATION
import ru.adavliatov.atomy.toolkit.fuel.service.WithHolderToHeader
import ru.adavliatov.atomy.toolkit.fuel.service.deserialize.WithFuelViewDeserializer

interface WithFuelFetchOrCreateAndHolder<Holder : WithHolder, View : Any> :
  WithFetchOrCreateAndHolder<Holder, View>,
  WithJsonContext,
  WithHolderToHeader<Holder>,
  WithFuelViewDeserializer<View>,
  WithFuelUrl {

  override fun fetchOrCreate(holder: Holder, item: View): View = url
    .httpPost()
    .jsonBody(context.asString(item))
    .header(AUTHORIZATION to holder.toHeader())
    .responseObject(viewDeserializer)
    .third
    .fold(
      { it },
      {
        //fixme adavliatov : introduce common approach
        throw ApiError(it.response.statusCode, Unknown, message = it.localizedMessage)
      }
    )
}