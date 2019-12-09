package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.example.transfer.domain.Account
import ru.adavliatov.atomy.example.transfer.domain.Name
import ru.adavliatov.atomy.toolkit.jooq.service.ModelJooqDaoAdapter
import javax.sql.DataSource

open class AccountJooqRepo(val ds: DataSource) : AccountRepo {
  override fun findByName(name: Name): Account? = TODO("not implemented")
  override fun findAll(): Iterable<Account> = TODO("not implemented")
  override fun findByIds(ids: Iterable<Id<Account>>): Set<Account> = TODO("not implemented")
  override fun create(models: Iterable<Account>) = TODO("not implemented")
  override fun modify(model: Account): Account = TODO("not implemented")
  override fun modify(models: Iterable<Account>) = TODO("not implemented")
  override fun removeByIds(ids: Iterable<Id<Account>>) = TODO("not implemented")
  override fun fetchOrCreate(model: Account): Account = TODO("not implemented")
}
