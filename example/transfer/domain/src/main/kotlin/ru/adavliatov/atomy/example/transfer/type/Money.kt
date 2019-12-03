package ru.adavliatov.atomy.example.transfer.type

import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.common.domain.error.code.*
import ru.adavliatov.atomy.common.ext.*
import ru.adavliatov.atomy.common.ext.ValidationExtensions.validate
import ru.adavliatov.atomy.example.transfer.type.AmountExtensions.isValid

typealias Currency = String
typealias Amount = Long

//todo: move to separate module
data class Money(
  val currency: Currency?,
  val amount: Amount
) {
  init {
    validateNotBlank(currency) { InvalidCurrencyError(currency) }
    validate(amount.isValid) { InvalidCurrencyError(currency) }
  }

  val isNegativeOrZero: Boolean
    get() = amount <= 0L

  fun gt(other: Money) = amount > other.amount
  fun lt(other: Money) = amount < other.amount
  fun ge(other: Money) = amount >= other.amount
  fun le(other: Money) = amount <= other.amount
}

object AmountExtensions {
  val Amount.isNegativeOrZero: Boolean
    get() = this <= 0L

  val Amount.isValid: Boolean
    get() = !isNegativeOrZero
}

object InvalidCurrency : CommonErrorCode("invalid-currency", "Invalid currency")
class InvalidCurrencyError(currency: Currency?) :
  InvalidArgumentError(InvalidCurrency, "[$currency] is invalid currency")

object InvalidAmount : CommonErrorCode("invalid-amount", "Invalid amount")
class InvalidAmountError(amount: Amount) : InvalidArgumentError(InvalidAmount, "[$amount] is invalid amount")
