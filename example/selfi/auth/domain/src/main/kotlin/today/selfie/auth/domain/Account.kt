package today.selfie.profile.domain

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.type.name.ValueHolder
import java.time.Instant

typealias OwnerId = Long

data class Account(
  override val id: Id<Account>,
  override val state: State,
  override val createdAt: Instant,
  override val modifiedAt: Instant = createdAt,

  val ownerId: OwnerId,
  val origin: Origin,
  val details: AccountDetails
) : WithModel<Account> {
  override fun withId(id: Id<Account>): Account = copy(id = id)
  override fun withState(state: State): Account = copy(state = state)
  override fun modified(ts: Instant): Account = copy(modifiedAt = ts)
}

data class Origin(val name: NameValue) : ValueHolder<NameValue>(name)

sealed class AccountDetails
object MissingDetails : AccountDetails()