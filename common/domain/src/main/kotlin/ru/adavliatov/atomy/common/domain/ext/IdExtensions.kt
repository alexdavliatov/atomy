package ru.adavliatov.atomy.common.domain.ext

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.type.ref.ConsumerRef
import java.util.UUID

object IdExtensions {
  fun <M : WithModel<M>> Iterable<Id<M>>.checkedIds(): Iterable<Long> = map { it.checkedId }
  fun <M : WithModel<M>> Iterable<Id<M>>.checkedIdsToArray(): Array<Long> = map { it.checkedId }
    .toLongArray()
    .toTypedArray()

  fun <M : WithModel<M>> Iterable<Id<M>>.checkedUids(): Iterable<UUID> = map { it.checkedUid }
  fun <M : WithModel<M>> Iterable<Id<M>>.checkedUidsToArray(): Array<UUID> = map { it.checkedUid }.toTypedArray()

  fun <M : WithModel<M>> Iterable<Id<M>>.checkedRefs(): Iterable<ConsumerRef> = map { it.checkedRef }
}
