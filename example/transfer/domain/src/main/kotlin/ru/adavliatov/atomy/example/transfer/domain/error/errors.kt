package ru.adavliatov.atomy.example.transfer.domain.error

import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.NotFoundError
import ru.adavliatov.atomy.example.transfer.domain.Operation
import ru.adavliatov.atomy.example.transfer.domain.error.code.TransferErrorCodes.InvalidOperationName
import ru.adavliatov.atomy.example.transfer.domain.error.code.TransferErrorCodes.OperationNotFound

class InvalidOperationNameError(name: String) : InvalidArgumentError(InvalidOperationName, "Invalid operation: [$name]")
class InvalidOperationNotFoundError(operation: Operation) :
    NotFoundError(OperationNotFound, "[${operation.name}] operation not found")
