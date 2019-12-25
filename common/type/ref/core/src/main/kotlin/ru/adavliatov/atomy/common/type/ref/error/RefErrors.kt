package ru.adavliatov.atomy.common.type.ref.error

import ru.adavliatov.atome.common.type.error.Error
import ru.adavliatov.atome.common.type.error.code.CommonErrorCode
import ru.adavliatov.atomy.common.type.ref.Ref

object RefErrors {
    class EmptyRefError(ref: Ref) : Error(EmptyRef, "Required ref $ref is empty")
    object EmptyRef : CommonErrorCode("empty-external-id", "Invalid client id")
}