package ru.adavliatov.atomy.common.ui.api.domain

import ru.adavliatov.atomy.common.type.error.Error
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCode
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.OK
import ru.adavliatov.atomy.common.ui.api.domain.error.toHttpError
import ru.adavliatov.atomy.common.ui.api.domain.error.toHttpErrorView

interface Response {
  val statusCode: StatusCode
  val body: Any?

  fun withStatusCode(statusCode: StatusCode = OK): Response
  fun <T> withBody(body: T): Response
  fun withError(apiError: ApiError) = withStatusCode(apiError.httpStatus)
    .withBody(apiError.toHttpErrorView())

  fun withError(error: Error) = withError(error.toHttpError())
}