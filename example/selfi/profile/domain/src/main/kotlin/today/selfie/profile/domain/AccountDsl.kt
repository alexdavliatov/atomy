package today.selfie.profile.domain

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.type.ref.imp.json.ext.RefExtensions.ref
import ru.adavliatov.common.type.json.impl.JacksonJson
import ru.adavliatov.common.type.json.impl.builder.JsonNodeBuilders.node
import today.selfie.Account.domain.Account
import today.selfie.Account.domain.MissingDetails
import today.selfie.Account.domain.Origin
import java.time.Instant

object AccountDsl {
    fun account(builder: AccountBuilder = AccountBuilder(), body: AccountBuilder.() -> Unit) =
        builder.apply(body).build()
}

@Suppress("MemberVisibilityCanBePrivate")
class AccountBuilder {
    val id: Id<Account> = Id.randomIdWith(
        ref(
            JacksonJson(
                node("id", "value").end()!!
            )
        )
    )
    var state = State("active")
    var createdAt = Instant.now()
    var modifiedAt = createdAt

    var ownerId = 0L
    var origin = Origin(NameValue("missing"))
    var details = MissingDetails

    fun build(): Account = Account(
        id, state, createdAt, modifiedAt, ownerId, origin, details
    )
}

