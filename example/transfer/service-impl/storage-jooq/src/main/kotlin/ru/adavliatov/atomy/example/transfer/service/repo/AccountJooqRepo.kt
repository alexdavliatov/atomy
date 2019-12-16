package ru.adavliatov.atomy.example.transfer.service.repo

import org.jooq.Constraint
import org.jooq.DAO
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedIds
import ru.adavliatov.atomy.example.transfer.domain.Account
import ru.adavliatov.atomy.example.transfer.domain.Name
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.Keys
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.Keys.*
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.Tables.ACCOUNTS
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.daos.AccountsDao
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.pojos.Accounts
import ru.adavliatov.atomy.example.transfer.service.repo.generated.account.tables.records.AccountsRecord
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.ModelJooqDaoAdapter
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqFetchOrCreateModel
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
  override fun findByIds(ids: Iterable<Id<Account>>): Set<Account> = dao.fetchById(*ids.checkedIds().toList().toTypedArray())
    .mapToSet { it.toModel() }
}
