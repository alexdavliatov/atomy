package ru.adavliatov.atomy.example.transfer.domain.error.code

import ru.adavliatov.atome.common.type.error.code.CommonErrorCode

object TransactionErrorCodes {
  object InvalidOperationName : CommonErrorCode("invalid-operation-name", "Invalid operation name")
  object OperationNotFound : CommonErrorCode("operation-not-found", "Operation not found")
}
