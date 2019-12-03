package ru.adavliatov.atomy.example.transfer.type

import ru.adavliatov.atomy.common.ext.ValidationExtensions.validate
import ru.adavliatov.atomy.example.transfer.type.AmountExtensions.isValid
import ru.adavliatov.atomy.example.transfer.type.AmountExtensions.zero
import ru.adavliatov.atomy.example.transfer.type.CurrencyExtensions.isValid
import ru.adavliatov.atomy.example.transfer.type.error.*

typealias Currency = String
typealias Amount = Long

/**
 * Use `org.javamoney:moneta` instead of this class. It is added as an example of a custom type.
 */
@Suppress("unused")
data class Money(
  val currency: Currency,
  val amount: Amount
) {
  init {
    validate(currency.isValid) { InvalidCurrencyError(currency) }
    validate(amount.isValid) { InvalidCurrencyError(currency) }
  }

  fun gt(other: Money) = amount > other.amount
  fun lt(other: Money) = amount < other.amount
  fun ge(other: Money) = amount >= other.amount
  fun le(other: Money) = amount <= other.amount

  fun zero() = MoneyExtensions.zero(currency)

  operator fun plus(other: Money): Money {
    validate(currency == other.currency) { DifferentCurrenciesError(currency, other.currency) }

    return copy(amount = amount + other.amount)
  }

  operator fun minus(other: Money): Money {
    validate(currency == other.currency) { DifferentCurrenciesError(currency, other.currency) }

    return copy(amount = amount - other.amount)
  }
}

object AmountExtensions {
  private val Amount?.isNegativeOrZero: Boolean
    get() = (this ?: 0L) <= 0L

  val Amount?.isValid: Boolean
    get() = !isNegativeOrZero

  const val zero: Amount = 0L
}

object CurrencyExtensions {
  val Currency?.isValid: Boolean
    get() = !isNullOrBlank()
}

object MoneyExtensions {
  fun zero(currency: Currency) = Money(currency, zero)
}
