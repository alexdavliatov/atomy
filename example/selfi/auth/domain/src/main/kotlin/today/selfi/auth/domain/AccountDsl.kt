package today.selfi.auth.domain

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.type.json.impl.builder.JsonNodeBuilders.node
import ru.adavliatov.atomy.common.type.name.NameValue
import today.selfi.profile.domain.Account
import today.selfi.profile.domain.MissingDetails
import today.selfi.profile.domain.Origin
import today.selfi.shared.type.ref.ext.RefExtensions.ref
import java.time.Instant

@Suppress("unused")
object AccountDsl {
  fun account(builder: AccountBuilder = AccountBuilder(), body: AccountBuilder.() -> Unit) =
    builder.apply(body).build()
}

@Suppress("MemberVisibilityCanBePrivate")
class AccountBuilder {
  val id: Id<Account> = Id.randomIdWith(
    ref(node("id", "value").end()!!)
  )
  var state = State("active")
  var createdAt: Instant = Instant.now()
  var modifiedAt: Instant = createdAt

  var ownerId = 0L
  var origin = Origin(NameValue("missing"))
  var details = MissingDetails

  fun build(): Account = Account(
    id, state, createdAt, modifiedAt, ownerId, origin, details
  )
}

