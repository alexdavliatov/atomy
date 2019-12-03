package ru.adavliatov.atomy.common.domain.ext

import ru.adavliatov.atomy.common.domain.ConsumerRef
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithEntity

object IdExtensions {
  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedIds(): Iterable<Long> = map { it.checkedId }
  fun <E : WithEntity<E>> Iterable<Id<E>>.checkedRefs(): Iterable<ConsumerRef> = map { it.checkedRef }
}
