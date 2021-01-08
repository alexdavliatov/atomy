package ru.adavliatov.atomy.common.service.event

import ru.adavliatov.atomy.common.type.event.Event

interface EventStore {
  fun <ModelId> new(event: Event<ModelId>)
  fun <ModelId> findByModelId(modelId: ModelId): List<ModelId>
}