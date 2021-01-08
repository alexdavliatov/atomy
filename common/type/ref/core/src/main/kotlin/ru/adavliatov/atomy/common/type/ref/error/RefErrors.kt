package ru.adavliatov.atomy.common.type.ref.error

import ru.adavliatov.atomy.common.type.error.*
import ru.adavliatov.atomy.common.type.error.code.*
import ru.adavliatov.atomy.common.type.ref.*

object RefErrors {
  class EmptyRefError(ref: Ref) : Error(EmptyRef, "Required ref $ref is empty")
  object EmptyRef : CommonErrorCode("empty-external-id", "Invalid client id")
}