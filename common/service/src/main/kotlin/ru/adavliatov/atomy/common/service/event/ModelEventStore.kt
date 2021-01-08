package ru.adavliatov.atomy.common.service.event

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.domain.event.ModelEvent

interface ModelEventStore {
  fun <M : WithModel<M>> new(event: ModelEvent<M>)
  fun <M : WithModel<M>> findFor(modelId: Id<M>): List<ModelEvent<M>>
}