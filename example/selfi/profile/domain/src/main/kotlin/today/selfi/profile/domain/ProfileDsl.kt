package today.selfi.profile.domain

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.json.impl.builder.JsonNodeBuilders.node
import ru.adavliatov.atomy.common.type.name.*
import today.selfi.shared.type.ref.ext.RefExtensions.ref
import java.time.Instant

@Suppress("unused")
object ProfileDsl {
  fun account(builder: ProfileBuilder = ProfileBuilder(), body: ProfileBuilder.() -> Unit) =
    builder.apply(body).build()
}

@Suppress("MemberVisibilityCanBePrivate")
class ProfileBuilder {
  val id: Id<Profile> = Id.randomIdWith(
    ref(node("id", "value").end()!!)
  )
  var state = State("active")
  var createdAt: Instant = Instant.now()
  var modifiedAt: Instant = createdAt
  var name: NameValue = NameValue("Bruce Wayne")

  fun build(): Profile = Profile(id, state, createdAt, modifiedAt, name)
}

