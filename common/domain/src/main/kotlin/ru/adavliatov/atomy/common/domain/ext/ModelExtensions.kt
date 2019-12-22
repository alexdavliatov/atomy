package ru.adavliatov.atomy.common.domain.ext

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet

object ModelExtensions {
  fun <M : WithId<M>> Iterable<M>.ids() = mapToSet { it.id }
  fun <M : WithId<M>> Iterable<M>.refs() = mapToSet { it.ref }
}
