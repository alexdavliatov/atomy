package ru.adavliatov.atomy.common.ui.api.serializer.ext

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel

object IdExtensions {
  inline fun <reified M : WithModel<M>> Id<*>.withModel(): Id<M> = Id.newId<M>(ref)
    .withId(id)
    .withUid(uid)
}