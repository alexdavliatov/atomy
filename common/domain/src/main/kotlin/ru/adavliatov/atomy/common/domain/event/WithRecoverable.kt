package ru.adavliatov.atomy.common.domain.event

import ru.adavliatov.atomy.common.domain.WithModel

interface WithRecoverable<M : WithModel<M>> {
  @Suppress("UNCHECKED_CAST")
  fun recover(events: List<ModelEvent<M>>): M = events
    .fold(this as M) { model, event -> event.applyTo(model) }
}