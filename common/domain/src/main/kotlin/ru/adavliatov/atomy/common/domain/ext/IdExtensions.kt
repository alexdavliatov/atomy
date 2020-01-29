package ru.adavliatov.atomy.common.domain.ext

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.type.ref.ConsumerRef
import java.util.UUID

object IdExtensions {
  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedIds(): Iterable<Long> = map { it.checkedId }
  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedIdsToArray(): Array<Long> = map { it.checkedId }
    .toLongArray()
    .toTypedArray()

  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedUids(): Iterable<UUID> = map { it.checkedUid }
  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedUidsToArray(): Array<UUID> = map { it.checkedUid }.toTypedArray()

  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedRefs(): Iterable<ConsumerRef> = map { it.checkedRef }
}
