package ru.adavliatov.atomy.common.ui.api.domain

import ru.adavliatov.atomy.common.ui.api.domain.error.*

interface Response {
  val statusCode: StatusCode

  fun withStatusCode(statusCode: StatusCode): Response
}