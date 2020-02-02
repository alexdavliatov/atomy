package ru.adavliatov.atomy.example.transfer.domain.error

import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors.NotFoundError
import ru.adavliatov.atomy.example.transfer.domain.*
import ru.adavliatov.atomy.example.transfer.domain.error.code.TransactionErrorCodes.InvalidOperationName
import ru.adavliatov.atomy.example.transfer.domain.error.code.TransactionErrorCodes.OperationNotFound

@Suppress("unused")
object TransactionErrors {
  class InvalidOperationNameError(name: String) :
    InvalidArgumentError(InvalidOperationName, "Invalid operation: [$name]")

  class InvalidOperationNotFoundError(operation: Operation) :
    NotFoundError(OperationNotFound, "[${operation.name}] operation not found")
}
