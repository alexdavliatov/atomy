package today.selfie.profile.domain

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.domain.WithModel
import java.time.Instant

data class Profile(
    override val id: Id<Profile>,
    override val state: State,
    override val createdAt: Instant,
    override val modifiedAt: Instant = createdAt
) : WithModel<Profile> {
    override fun withId(id: Id<Profile>): Profile = copy(id = id)
    override fun withState(state: State): Profile = copy(state = state)
    override fun modified(ts: Instant): Profile = copy(modifiedAt = ts)
}