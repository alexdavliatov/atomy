package ru.adavliatov.atomy.toolkit.fuel.ext

import com.github.kittinunf.fuel.core.FuelError
import ru.adavliatov.atomy.common.type.json.JsonContext
import ru.adavliatov.atomy.common.ui.api.domain.error.InternalServerError
import ru.adavliatov.atomy.common.ui.api.view.ApiErrorView
import ru.adavliatov.atomy.common.ui.api.ext.ApiErrorExtensions.toApiError as asApiError

object FuelErrorExtensions {
  fun FuelError.toApiError() = { context: JsonContext ->
    String(response.data)
      .takeIf { it.isNotBlank() }
      ?.let { context.fromString(it, ApiErrorView::class.java) }
      ?.asApiError()
      ?: InternalServerError()
  }
}