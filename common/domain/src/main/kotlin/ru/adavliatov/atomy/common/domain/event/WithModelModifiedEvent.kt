package ru.adavliatov.atomy.common.domain.event

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.type.event.EventId
import ru.adavliatov.atomy.common.type.name.NameValue
import java.time.Instant
import java.util.UUID

interface WithModelModifiedEvent<M : WithModel<M>, T> : WithModelEvent<M> {
  val newValue: T
}

abstract class ModelModifiedEvent<M : WithModel<M>, T>(
  id: EventId = UUID.randomUUID(),
  name: NameValue = NameValue("state-modified"),
  modelId: Id<M>,
  createdAt: Instant = Instant.now(),
  modifiedAt: Instant = createdAt,

  override val newValue: T
) : ModelEvent<M>(id, name, modelId, createdAt, modifiedAt),
  WithModelModifiedEvent<M, T>

data class ModifiedStateEvent<M : WithModel<M>>(
  override val id: EventId = UUID.randomUUID(),
  override val name: NameValue = NameValue("state-modified"),
  override val modelId: Id<M>,
  override val createdAt: Instant = Instant.now(),
  override val modifiedAt: Instant = createdAt,
  override val newValue: State
) : ModelModifiedEvent<M, State>(id, name, modelId, createdAt, modifiedAt, newValue) {
  override fun applyTo(model: M): M = model
    .modified(modifiedAt)
    .withState(newValue)

}