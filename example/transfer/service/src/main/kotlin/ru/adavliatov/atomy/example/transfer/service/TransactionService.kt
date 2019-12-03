package ru.adavliatov.atomy.example.transfer.service

import ru.adavliatov.atomy.example.transfer.domain.*
import ru.adavliatov.atomy.example.transfer.service.repo.*
import ru.adavliatov.atomy.example.transfer.type.*

class TransactionService(
  private val accountRepo: AccountRepo,
  private val transactionRepo: TransactionRepo
) {
  fun deposit(account: Account, money: Money): Transaction = TODO()
  fun withdraw(account: Account, money: Money): Transaction = TODO()
  fun transfer(from: Account, to: Account, money: Money): Transaction = TODO()
}
