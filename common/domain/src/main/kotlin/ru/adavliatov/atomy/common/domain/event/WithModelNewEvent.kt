package ru.adavliatov.atomy.common.domain.event

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.ext.UuidExtensions.uuid
import ru.adavliatov.atomy.common.type.event.EventId
import ru.adavliatov.atomy.common.type.name.NameValue
import java.time.Instant
import java.util.UUID

interface WithModelNewEvent<M : WithModel<M>> : WithModelEvent<M>

data class ModelNewEvent<M : WithModel<M>>(
  override val id: EventId = UUID.randomUUID(),
  override val name: NameValue = NameValue("new"),
  override val modelId: Id<M>,
  override val createdAt: Instant = Instant.now(),
  override val modifiedAt: Instant = createdAt
) : ModelEvent<M>(id, name, modelId, createdAt, modifiedAt),
  WithModelNewEvent<M> {
  /**
   * Applies this event to dummy model.
   * Model should have state already applied.
   */
  override fun applyTo(model: M): M = model
    .withId(modelId)
    .created(createdAt)
    .modified(modifiedAt)
}

object ModelNewEventDSL {
  fun <M : WithModel<M>> newEvent(
    modelId: Id<M>,
    builder: ModelNewEventBuilder<M> = ModelNewEventBuilder(modelId),
    body: ModelNewEventBuilder<M>.() -> Unit
  ) = builder.apply(body).build()

}

class ModelNewEventBuilder<M : WithModel<M>>(var modelId: Id<M>) {
  var id = "00000000-0000-0000-0000-000000000000".uuid()
  var name: NameValue = NameValue("new")
  var createdAt = Instant.now()
  var modifiedAt = createdAt

  fun build(): ModelNewEvent<M> = ModelNewEvent(
    id, name, modelId, createdAt, modifiedAt
  )
}