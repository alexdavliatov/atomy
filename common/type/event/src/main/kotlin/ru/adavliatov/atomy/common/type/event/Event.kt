package ru.adavliatov.atomy.common.type.event

import ru.adavliatov.atomy.common.type.name.NameValue
import java.util.UUID

interface Event<ModelId> {
  val id: EventId
  val name: NameValue
  val modelId: ModelId
}
typealias EventId = UUID