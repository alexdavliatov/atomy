package ru.adavliatov.atomy.example.transfer.domain

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.example.transfer.type.*
import ru.adavliatov.atomy.example.transfer.type.MoneyExtensions.zero
import java.time.Instant

typealias Name = String

@Suppress("unused")
data class Account(
  override val id: Id<Account>,
  override val state: State,
  override val createdAt: Instant,
  override val modifiedAt: Instant,

  val name: Name,
  val wallet: Map<Currency, Money>
) : WithModel<Account> {
  override fun withId(id: Id<Account>): Account = copy(id = id)
  override fun withState(state: State): Account = copy(state = state)
  override fun modified(ts: Instant): Account = copy(modifiedAt = ts)

  private fun withMoney(money: Money): Account {
    val newWallet = wallet.plus(money.currency to money)
    return copy(wallet = newWallet)
  }

  fun plus(money: Money): Account {
    val currentMoney = wallet.getOrDefault(money.currency, money.zero())
    return withMoney(currentMoney + money)
  }

  fun minus(money: Money): Account {
    val currentMoney = wallet.getOrDefault(money.currency, money.zero())
    return withMoney(currentMoney + money)
  }

  fun moneyForCurrency(currency: Currency): Money = wallet
    .withDefault { zero(currency) }
    .getValue(currency)

  fun deposit(money: Money): Account = copy()
}
