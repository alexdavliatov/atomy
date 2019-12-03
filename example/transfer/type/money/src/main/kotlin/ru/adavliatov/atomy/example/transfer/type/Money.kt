package ru.adavliatov.atomy.example.transfer.type

import ru.adavliatov.atomy.common.ext.*
import ru.adavliatov.atomy.common.ext.ValidationExtensions.validate
import ru.adavliatov.atomy.example.transfer.type.AmountExtensions.isValid
import ru.adavliatov.atomy.example.transfer.type.error.*

typealias Currency = String
typealias Amount = Long

@Suppress("unused")
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
  private val Amount.isNegativeOrZero: Boolean
    get() = this <= 0L

  val Amount.isValid: Boolean
    get() = !isNegativeOrZero
}

