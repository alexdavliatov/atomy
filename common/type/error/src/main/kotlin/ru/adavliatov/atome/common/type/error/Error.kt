package ru.adavliatov.atome.common.type.error

import ru.adavliatov.atome.common.type.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atome.common.type.error.code.*
import ru.adavliatov.atome.common.type.error.code.ErrorCodes.BadRequest
import ru.adavliatov.atome.common.type.error.code.ErrorCodes.MultipleErrorsOccurred
import ru.adavliatov.atome.common.type.error.code.ErrorCodes.NotFound
import ru.adavliatov.atome.common.type.error.code.ErrorCodes.Unknown

open class Error(
  val code: ErrorCode = Unknown,
  override val message: String = code.message,
  cause: Throwable? = null
) : RuntimeException(message, cause)

@Suppress("unused")
object Errors {
  object UnknownError : Error()

  class MultipleErrors(
    code: ErrorCode = MultipleErrorsOccurred,
    message: String = code.message,
    val items: Iterable<Error>
  ) : InvalidArgumentError(MultipleErrorsOccurred, message)
}

@Suppress("unused")
object HttpWrapperErrors {
  open class InvalidArgumentError(
    code: ErrorCode = BadRequest,
    message: String = code.message,
    cause: Throwable? = null
  ) : Error(code, message, cause)

  open class NotFoundError(
    code: ErrorCode = NotFound,
    message: String = code.message,
    cause: Throwable? = null
  ) : Error(code, message, cause)

  open class InternalError(
    errorCode: ErrorCode = ErrorCodes.InternalError,
    message: String = errorCode.message,
    cause: Throwable? = null
  ) : Error(errorCode, message, cause)
}
