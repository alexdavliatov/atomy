package ru.adavliatov.atomy.common.domain.event

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithAudited
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.type.event.Event
import ru.adavliatov.atomy.common.type.event.EventId
import ru.adavliatov.atomy.common.type.name.NameValue
import java.time.Instant
import java.util.UUID

interface WithModelEvent<M : WithModel<M>> : Event<Id<M>>, WithAudited {
  fun applyTo(model: M): M
}

abstract class ModelEvent<M : WithModel<M>>(
  override val id: EventId = UUID.randomUUID(),
  override val name: NameValue,
  override val modelId: Id<M>,
  override val createdAt: Instant = Instant.now(),
  override val modifiedAt: Instant = Instant.now()
) : WithModelEvent<M>