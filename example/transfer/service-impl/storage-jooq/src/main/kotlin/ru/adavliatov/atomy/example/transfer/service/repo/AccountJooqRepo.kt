package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.example.transfer.domain.*
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.Keys.*
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.daos.*
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.pojos.*
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.records.*
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.*
import javax.sql.DataSource

open class AccountJooqRepo(ds: DataSource) : ModelJooqDaoAdapter<Account, AccountsRecord, Accounts>(ds), 
  AccountRepo,
  WithJooqFetchOrCreateModel<Account, AccountsRecord, Accounts> {
  override val dao: AccountsDao = AccountsDao(ds.toJooqConfig())

  override val entityClass = Account::class.java
  override val pojoClass = Accounts::class.java

  override val insertOnDuplicateIgnoreConstraint = ACCOUNTS_NAME_UNQ.constraint()

  override fun Account.toPojo(): Accounts = Accounts(
    id.checkedId,
    id.uid,
    null,
    state.name,
    createdAt,
    modifiedAt,
    name,
    null
  )

  override fun Accounts.toModel(): Account = Account(
    Id(id, uid, null!!, Account::class),
    State(state),
    createdAt,
    modifiedAt,
    name,
    mapOf()
  )

  override fun findByName(name: Name): Account? = dao.fetchOneByName(name).toModel()
}
