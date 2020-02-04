package ru.adavliatov.atomy.common.ui.api.domain.error

//import ru.adavliatov.atome.common.type.error.code.ErrorCodes.UnknownError
import ru.adavliatov.atomy.common.type.error.*
import ru.adavliatov.atomy.common.type.error.code.*
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
import java.util.UUID.*

typealias StatusCode = Int

@Suppress("unused")
object StatusCodes {
  const val OK = 200
  const val CREATED = 201
  const val ACCEPTED = 201
  const val NO_CONTENT = 204

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
fun Error.toHttpError(httpStatus: StatusCode = 500): ApiError = ApiError(httpStatus, this)

@Suppress("unused")
open class ConflictOccurredError(errorCode: ErrorCode = ConflictOccured, message: String = errorCode.message) :
  ApiError(CONFLICT, errorCode, message = message) {
  constructor(message: String) : this(ConflictOccured, message)
  constructor(errorCode: ErrorCode = ConflictOccured) : this(errorCode, errorCode.message)
}

@Suppress("unused")
open class NotFoundError(errorCode: ErrorCode = NotFound, message: String = errorCode.message) :
  ApiError(NOT_FOUND, errorCode, message = message) {
  constructor(error: Error) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = NotFound) : this(Error(errorCode))
  constructor(message: String) : this(Error(NotFound, message))
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
  constructor(error: Error) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = BadRequest) : this(errorCode, errorCode.message)
  constructor(message: String) : this(Error(BadRequest, message))
}

@Suppress("unused")
open class InternalServerError(errorCode: ErrorCode = InternalError, message: String = errorCode.message) :
  ApiError(INTERNAL_SERVER_ERROR, errorCode, message = message) {
  constructor(error: Error) : this(error.code, error.message)
  constructor(errorCode: ErrorCode = InternalError) : this(errorCode, errorCode.message)
  constructor(message: String) : this(InternalError, message)
}

//@Suppress("unused")
//class RawError(httpStatus: StatusCode, error: Error = UnknownError) : ApiError(httpStatus, error)

@Suppress("unused")
object NotImplementedError : ApiError(NOT_IMPLEMENTED, NotImplemented)
