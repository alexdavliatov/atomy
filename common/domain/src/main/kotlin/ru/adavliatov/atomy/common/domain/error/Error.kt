package ru.adavliatov.atomy.common.domain.error

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.common.domain.error.code.*
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.BadRequest
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.EmptyId
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.EmptyRef
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.InvalidClientId
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.InvalidStateName
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.MultipleErrorsOccurred
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.NotFound
import ru.adavliatov.atomy.common.domain.error.code.ErrorCodes.Unknown

open class Error(
  private val code: ErrorCode = Unknown,
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

@Suppress("unused")
object DomainErrors {
  object InvalidStateNameError : Error(InvalidStateName)

  class EmptyIdError(id: Id<*>) : Error(EmptyId, "Required id $id is empty")
  class EmptyRefError(ref: Ref) : Error(EmptyRef, "Required ref $ref is empty")
  object InvalidConsumerError : Error(InvalidClientId)
}
