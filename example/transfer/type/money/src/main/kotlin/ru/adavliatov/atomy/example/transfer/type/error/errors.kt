package ru.adavliatov.atomy.example.transfer.type.error

import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.example.transfer.type.*
import ru.adavliatov.atomy.example.transfer.type.error.code.*

class InvalidCurrencyError(currency: Currency?) :
  InvalidArgumentError(InvalidCurrency, "[$currency] is invalid currency")

@Suppress("unused")
class InvalidAmountError(amount: Amount) : InvalidArgumentError(
  InvalidAmount, "[$amount] is invalid amount"
)

class DifferentCurrenciesError(first: Currency, second: Currency) :
  InvalidArgumentError(code = DifferentCurrencies, message = "[$first] currency is not equal to [$second]")
