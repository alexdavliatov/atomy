package ru.adavliatov.atomy.common.domain.ext

import ru.adavliatov.atomy.common.domain.*

object IdExtensions {
  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedIds(): Iterable<Long> = map { it.checkedId }
  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedIdsToArray(): Array<Long> =
    map { it.checkedId }.toLongArray().toTypedArray()

  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedRefs(): Iterable<ConsumerRef> = map { it.checkedRef }
}
