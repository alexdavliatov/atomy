package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.type.ref.ConsumerId
import java.util.UUID

interface WithHolder {
}

interface WithConsumerHolder : WithHolder {
  val consumer: ConsumerId
}
interface WithOwnerHolder : WithConsumerHolder {
  val ownerId: OwnerId
}

typealias OwnerId = UUID
data class OwnerHolder(
  override val consumer: ConsumerId,
  override val ownerId: OwnerId
) : WithOwnerHolder
