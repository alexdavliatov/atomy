package ru.adavliatov.atomy.example.transfer.type.error

import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors
import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.example.transfer.type.*
import ru.adavliatov.atomy.example.transfer.type.error.code.InvalidAmount
import ru.adavliatov.atomy.example.transfer.type.error.code.InvalidCurrency

class InvalidCurrencyError(currency: Currency?) :
  InvalidArgumentError(InvalidCurrency, "[$currency] is invalid currency")

@Suppress("unused")
class InvalidAmountError(amount: Amount) : InvalidArgumentError(
    InvalidAmount, "[$amount] is invalid amount")
