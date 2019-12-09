package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.example.transfer.domain.*
import javax.sql.DataSource

open class TransactionJooqRepo(val ds: DataSource) : TransactionRepo {
  override fun findAll(): Iterable<Transaction> = TODO("not implemented")
  override fun findByIds(ids: Iterable<Id<Transaction>>): Set<Transaction> = TODO("not implemented")
  override fun create(models: Iterable<Transaction>) = TODO("not implemented")
  override fun modify(model: Transaction): Transaction = TODO("not implemented")
  override fun modify(models: Iterable<Transaction>) = TODO("not implemented")
  override fun removeByIds(ids: Iterable<Id<Transaction>>) = TODO("not implemented")
}
