package ru.adavliatov.atomy.example.transfer.domain.error.code

import ru.adavliatov.atomy.common.type.error.code.*

object TransactionErrorCodes {
  object InvalidOperationName : CommonErrorCode("invalid-operation-name", "Invalid operation name")
  object OperationNotFound : CommonErrorCode("operation-not-found", "Operation not found")
}
