package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.example.transfer.domain.Account
import ru.adavliatov.atomy.example.transfer.domain.Name
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.keys.ACCOUNTS_NAME_UNQ
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.daos.AccountsDao
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.pojos.Accounts
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.records.AccountsRecord
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.ModelJooqDaoAdapter
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFetchOrCreate
import javax.sql.DataSource

open class AccountJooqRepo(ds: DataSource) : ModelJooqDaoAdapter<Account, AccountsRecord, Accounts>(ds),
  AccountRepo,
  WithJooqFetchOrCreate<Account, AccountsRecord, Accounts> {
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
    Id(id, uid, ref(requireNotNull(ref)), Account::class.java),
    State(requireNotNull(state)),
    requireNotNull(createdAt),
    requireNotNull(modifiedAt),
    requireNotNull(name),
    mapOf()
  )

  override fun findByName(name: Name): Account? = dao.fetchOneByName(name)?.toModel()
}
