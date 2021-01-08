package ru.adavliatov.atomy.common.ui.api.ext

import ru.adavliatov.atomy.common.type.error.code.CommonErrorCode
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.view.ApiErrorView

object ApiErrorExtensions {
  fun ApiError.toView() = ApiErrorView(httpStatus, errorCode.code, requestId, message ?: errorCode.message)
  fun ApiErrorView.toApiError() = ApiError(
    httpStatus,
    GenericErrorCode(code),
    requestId,
    message
  )
}

class GenericErrorCode(code: String) : CommonErrorCode(code, code)