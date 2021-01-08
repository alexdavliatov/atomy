package ru.adavliatov.atomy.common.ui.api.plugin.javalin.domain.error

import io.javalin.Javalin
import ru.adavliatov.atomy.common.type.error.Error
import ru.adavliatov.atomy.common.ui.api.WithErrorHandler
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.domain.error.toHttpError
import ru.adavliatov.atomy.common.ui.api.domain.error.toHttpErrorView

object JavalinErrorHandler : WithErrorHandler {
  operator fun invoke(javalin: Javalin) {
    javalin.exception(ApiError::class.java) { apiError, ctx ->
      ctx
        .status(apiError.httpStatus)
        .json(apiError.toHttpErrorView())
    }
    javalin.exception(Error::class.java) { error, ctx ->
      val apiError = error.toHttpError()

      ctx
        .status(apiError.httpStatus)
        .json(apiError.toHttpErrorView())
    }
  }
}