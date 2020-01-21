package today.selfi.auth.service.repo

import org.jooq.SQLDialect.POSTGRES_10
import ru.adavliatov.atomy.common.type.ref.impl.json.*
import ru.adavliatov.atomy.example.selfie.profile.service.repo.generated.account.tables.daos.*
import ru.adavliatov.atomy.example.selfie.profile.service.repo.generated.account.tables.pojos.*
import ru.adavliatov.atomy.example.selfie.profile.service.repo.generated.account.tables.records.*
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.*
import ru.adavliatov.common.type.json.impl.*
import today.selfie.profile.domain.Account
import javax.sql.DataSource

open class AccountJooqRepo(ds: DataSource) : ModelJooqDaoAdapter<Account, AccountsRecord, Accounts>(ds),
  AccountRepo {
  override val dao: AccountsDao = AccountsDao(ds.toJooqConfig().set(POSTGRES_10))

  override val entityClass: Class<Account> = Account::class.java
  override val pojoClass: Class<Accounts> = Accounts::class.java

  override fun Account.toPojo(): Accounts = Accounts(
    id.checkedId,
    id.uid,
    id.ref.consumer.let { it as JsonConsumerId<*> }.let { it.value as JacksonJson },
    id.ref.ref?.let { it as JsonConsumerId<*> }?.let { it.value as JacksonJson },
    state.name,
    createdAt,
    modifiedAt,
    ownerId,
    origin.name.name,
    null
  )

  override fun Accounts.toModel(): Account = Account(
    Id(
      id,
      uid,
      Ref(
        JsonConsumerId(consumer),
        JsonConsumerRef(ref)
      ),
      Account::class
    ),
    State(state),
    createdAt,
    modifiedAt,
    ownerId,
    Origin(NameValue(origin)),
    MissingDetails
  )
}