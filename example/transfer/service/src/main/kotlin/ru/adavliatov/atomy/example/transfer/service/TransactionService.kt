package ru.adavliatov.atomy.example.transfer.service

import ru.adavliatov.atomy.example.transfer.domain.Account
import ru.adavliatov.atomy.example.transfer.domain.Transaction
import ru.adavliatov.atomy.example.transfer.service.repo.AccountRepo
import ru.adavliatov.atomy.example.transfer.service.repo.TransactionRepo
import ru.adavliatov.atomy.example.transfer.type.Money

@Suppress("unused")
class TransactionService(
  private val accountRepo: AccountRepo,
  private val transactionRepo: TransactionRepo
) {
  fun deposit(account: Account, money: Money): Transaction = TODO()
  fun withdraw(account: Account, money: Money): Transaction = TODO()
  fun transfer(from: Account, to: Account, money: Money): Transaction = TODO()
}
