package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.example.transfer.domain.Transaction
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.daos.*
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.pojos.*
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.records.*
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.ModelJooqDaoAdapter
import javax.sql.DataSource

open class TransactionJooqRepo(ds: DataSource) : ModelJooqDaoAdapter<Transaction, TransactionsRecord, Transactions>(ds),
  TransactionRepo {
  override val dao = TransactionsDao(ds.toJooqConfig())

  override val entityClass = Transaction::class.java
  override val pojoClass = Transactions::class.java

  override fun Transaction.toPojo(): Transactions = Transactions(
    id.checkedId,
    id.uid,
    null,
    state.name,
    createdAt,
    modifiedAt,
    from?.id,
    to.id,
    operation.name,
    null
  )

  override fun Transactions.toModel(): Transaction = Transaction(
    Id(id, uid, null!!, Transaction::class),
    State(state),
    createdAt,
    modifiedAt,
    Id(id = fromAccount, ref = null!!, model = Account::class),
    Id(id = toAccount, ref = null!!, model = Account::class),
    null!!,
    Operation(operation)
  )

}
