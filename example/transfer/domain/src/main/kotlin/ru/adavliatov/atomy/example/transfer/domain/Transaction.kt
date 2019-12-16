package ru.adavliatov.atomy.example.transfer.domain

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.ext.ValidationExtensions.validate
import ru.adavliatov.atomy.example.transfer.domain.Operations.ITEMS
import ru.adavliatov.atomy.example.transfer.domain.error.TransactionErrors
import ru.adavliatov.atomy.example.transfer.domain.error.TransactionErrors.InvalidOperationNameError
import ru.adavliatov.atomy.example.transfer.type.Money
import java.time.Instant

data class Transaction(
  override val id: Id<Transaction>,
  override val state: State,
  override val createdAt: Instant,
  override val modifiedAt: Instant,

  val from: Id<Account>?,
  val to: Id<Account>,
  val money: Money,
  val operation: Operation
) : WithModel<Transaction> {
  override fun withId(id: Id<Transaction>): Transaction = copy(id = id)
  override fun withState(state: State): Transaction = copy(state = state)
  override fun modified(ts: Instant): Transaction = copy(modifiedAt = ts)
}

data class Operation(val name: String) {
  init {
    validate(name.isNotBlank()) { InvalidOperationNameError(name) }
    validate(this in ITEMS) { InvalidOperationNameError(name) }
  }
}

object Operations {
  val DEPOSIT = Operation("deposit")
  val WITHDRAW = Operation("withdraw")
  val TRANSFER = Operation("transfer")

  val ITEMS = setOf(DEPOSIT, WITHDRAW, TRANSFER)
}
