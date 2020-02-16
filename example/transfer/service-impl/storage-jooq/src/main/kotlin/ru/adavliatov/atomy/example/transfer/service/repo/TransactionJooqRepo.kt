package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson
import ru.adavliatov.atomy.common.type.ref.impl.json.ext.RefExtensions.ref
import ru.adavliatov.atomy.example.transfer.domain.Account
import ru.adavliatov.atomy.example.transfer.domain.Operation
import ru.adavliatov.atomy.example.transfer.domain.Transaction
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.daos.TransactionsDao
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.pojos.Transactions
import ru.adavliatov.atomy.example.transfer.service.repo.generated.transaction.tables.records.TransactionsRecord
import ru.adavliatov.atomy.example.transfer.type.Money
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
    Id(id, uid, ref(ref), Transaction::class.java),
    State(state),
    createdAt,
    modifiedAt,
    Id(id = fromAccount, ref = ref(ref), model = Account::class.java),
    Id(id = toAccount, ref = ref(ref), model = Account::class.java),
    Money(
//      money.node["currency"].asText(),
//      money.node["amount"].asLong()
      "gbp",
      0L
    ),
    Operation(operation)
  )

}

fun ref(json: JacksonJson) = ref(
  json.node.get("consumer").toJson()
)
