package ru.adavliatov.atomy.common.ui.api.domain.error

import ru.adavliatov.atomy.common.type.error.Error
import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors
import ru.adavliatov.atomy.common.type.error.code.ErrorCode
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.BadRequest
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.ConflictOccured
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Forbidden
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.InternalError
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.NotFound
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.NotImplemented
import ru.adavliatov.atomy.common.type.error.code.ErrorCodes.Unauthorized
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.BAD_REQUEST
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.CONFLICT
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.FORBIDDEN
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.INTERNAL_SERVER_ERROR
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.NOT_FOUND
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.NOT_IMPLEMENTED
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.UNAUTHORIZED
import ru.adavliatov.atomy.common.ui.api.ext.ApiErrorExtensions.toView
import ru.adavliatov.atomy.common.ui.api.view.ApiErrorView
import java.util.UUID.randomUUID

typealias StatusCode = Int

@Suppress("unused")
object StatusCodes {
  const val OK = 200
  const val CREATED = 201
  const val ACCEPTED = 201
  const val NO_CONTENT = 204

  const val MOVED_TEMPORARILY = 302

  const val BAD_REQUEST = 400
  const val UNAUTHORIZED = 401
  const val FORBIDDEN = 403
  const val NOT_FOUND = 404
  const val CONFLICT = 409

  const val INTERNAL_SERVER_ERROR = 500
  const val NOT_IMPLEMENTED = 501
}

@Suppress("unused")
open class ApiError(
  val httpStatus: StatusCode,
  val errorCode: ErrorCode,
  val requestId: String = randomUUID().toString(),
  message: String = errorCode.message
) : RuntimeException(message) {
  constructor(httpStatus: StatusCode, error: Error) : this(httpStatus, error.code, message = error.message)
}

@Suppress("unused")
fun Error.toHttpError(): ApiError = when (this) {
  is HttpWrapperErrors.InvalidArgumentError -> BadRequestError(this)
  is HttpWrapperErrors.NotFoundError -> NotFoundError(this)
  is HttpWrapperErrors.InternalError -> InternalServerError(this)
  else -> InternalServerError(this)
}

@Suppress("unused")
fun Error.toHttpErrorView(): ApiErrorView = toHttpError().toView()

@Suppress("unused")
fun ApiError.toHttpErrorView(): ApiErrorView = toView()

@Suppress("unused")
open class ConflictOccurredError(errorCode: ErrorCode = ConflictOccured, message: String = errorCode.message) :
  ApiError(CONFLICT, errorCode, message = message) {
  constructor(message: String) : this(ConflictOccured, message)
  constructor(errorCode: ErrorCode = ConflictOccured) : this(errorCode, errorCode.message)
}

@Suppress("unused")
open class NotFoundError(errorCode: ErrorCode = NotFound, message: String = errorCode.message) :
  ApiError(NOT_FOUND, errorCode, message = message) {
  constructor(error: HttpWrapperErrors.NotFoundError) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = NotFound) : this(HttpWrapperErrors.NotFoundError(errorCode))
  constructor(message: String) : this(HttpWrapperErrors.NotFoundError(NotFound, message))
}

@Suppress("unused")
open class PermissionDeniedError(errorCode: ErrorCode = Forbidden, message: String = errorCode.message) :
  ApiError(FORBIDDEN, errorCode, message = message) {
  constructor(error: Error) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = Forbidden) : this(Error(errorCode))
  constructor(message: String) : this(Error(Forbidden, message))
}

@Suppress("unused")
open class AuthorizationFailedError(errorCode: ErrorCode = Unauthorized, message: String = errorCode.message) :
  ApiError(UNAUTHORIZED, errorCode, message = message) {
  constructor(error: Error) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = Unauthorized) : this(Error(errorCode))
  constructor(message: String) : this(Error(Unauthorized, message))
}

@Suppress("unused")
open class BadRequestError(errorCode: ErrorCode = BadRequest, message: String = errorCode.message) :
  ApiError(BAD_REQUEST, errorCode, message = message) {
  constructor(error: HttpWrapperErrors.InvalidArgumentError) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = BadRequest) : this(errorCode, errorCode.message)
  constructor(message: String) : this(HttpWrapperErrors.InvalidArgumentError(BadRequest, message))
}

@Suppress("unused")
open class InternalServerError(errorCode: ErrorCode = InternalError, message: String = errorCode.message) :
  ApiError(INTERNAL_SERVER_ERROR, errorCode, message = message) {
  constructor(error: HttpWrapperErrors.InternalError) : this(error.code, error.message)
  constructor(error: Error) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = InternalError) : this(errorCode, errorCode.message)
  constructor(message: String) : this(InternalError, message)
}

//@Suppress("unused")
//class RawError(httpStatus: StatusCode, error: Error = UnknownError) : ApiError(httpStatus, error)

@Suppress("unused")
object NotImplementedError : ApiError(NOT_IMPLEMENTED, NotImplemented)
