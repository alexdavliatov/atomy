package ru.adavliatov.atomy.common.ui.api.domain.error

import ru.adavliatov.atomy.common.type.error.code.CommonErrorCode

object AccessErrorCodes {
  object CanNotCreate : CommonErrorCode(code = "can-not-create", message = "Can not create resource")
  object CanNotModify : CommonErrorCode(code = "can-not-modify", message = "Can not modify resource")
  object CanNotRemove : CommonErrorCode(code = "can-not-remove", message = "Can not remove resource")
  object CanNotAccess : CommonErrorCode(code = "can-not-access", message = "Can not access resource")
}