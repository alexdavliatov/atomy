package ru.adavliatov.atomy.common.domain.error.code

interface ErrorCode {
  val code: String
  val message: String
}

abstract class CommonErrorCode(
  override val code: String,
  override val message: String
) : ErrorCode

@Suppress("unused")
object ErrorCodes {
  object Unknown : CommonErrorCode("unknown", "Unknown error")

  //http status wrappers
  object Forbidden : CommonErrorCode("forbidden", "Permission denied")

  object Unauthorized : CommonErrorCode("unauthorized", "Authorization failed")
  object NotFound : CommonErrorCode("not-found", "Resource not found")
  object BadRequest : CommonErrorCode("bad-request", "Bad request")
  object InternalError : CommonErrorCode("internal", "Internal server error")
  object ConflictOccured : CommonErrorCode("internal", "Internal server error")
  object NotImplemented : CommonErrorCode("not-implemented", "Not implemented error")
  object MultipleErrorsOccurred : CommonErrorCode("multiple-errors-occurred", "Multiple Errors Occurred")

  //domain error codes
  object InvalidStateName : CommonErrorCode("invalid-state-name", "Invalid state name")

  object InvalidClientId : CommonErrorCode("invalid-client-id", "Invalid client id")

  object EmptyId : CommonErrorCode("empty-id", "Invalid client id")
  object EmptyRef : CommonErrorCode("empty-external-id", "Invalid client id")
}
