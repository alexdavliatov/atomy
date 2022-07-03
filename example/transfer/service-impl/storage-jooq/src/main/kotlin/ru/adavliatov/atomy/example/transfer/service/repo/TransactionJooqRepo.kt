package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson.Companion.toJson
import ru.adavliatov.atomy.common.type.ref.impl.json.ext.RefExtensions.ref
import ru.adavliatov.atomy.example.transfer.domain.Account
import ru.adavliatov.atomy.example.transfer.domain.Operation
import ru.adavliatov.atomy.example.transfer.domain.Transaction
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.daos.TransactionsDao
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.pojos.Transactions
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.records.TransactionsRecord
import ru.adavliatov.atomy.example.transfer.type.Money
import ru.adavliatov.atomy.toolkit.jooq.ext.PostgresJooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.ModelJooqDaoAdapter
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFetchOrCreateOrIgnore
import javax.sql.DataSource

open class TransactionJooqRepo(ds: DataSource) : ModelJooqDaoAdapter<Transaction, TransactionsRecord, Transactions>(ds),
  WithJooqFetchOrCreateOrIgnore<Transaction, TransactionsRecord, Transactions>,
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
    Id(id, uid, ref(requireNotNull(ref)), Transaction::class.java),
    State(requireNotNull(state)),
    requireNotNull(createdAt),
    requireNotNull(modifiedAt),
    Id(id = fromAccount, ref = ref(requireNotNull(ref)), model = Account::class.java),
    Id(id = toAccount, ref = ref(requireNotNull(ref)), model = Account::class.java),
    Money(
//      money.node["currency"].asText(),
//      money.node["amount"].asLong()
      "gbp",
      0L
    ),
    Operation(requireNotNull(operation))
  )

}

fun ref(json: JacksonJson) = ref(
  json.node.get("consumer").toJson()
)
