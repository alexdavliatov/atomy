package ru.adavliatov.atomy.common.ui.api.view

import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCode
import java.util.UUID

//todo (adavliatov) introduce UIErrorView
data class ApiErrorView(
  val httpStatus: StatusCode,
  val code: String,
  val requestId: String = UUID.randomUUID().toString(),
  val message: String
)