package ru.adavliatov.atomy.common.ui.api.domain

import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCode
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.OK

interface Response {
  val statusCode: StatusCode
  val body: Any?

  fun withStatusCode(statusCode: StatusCode = OK): Response
  fun <T> withBody(body: T): Response
}