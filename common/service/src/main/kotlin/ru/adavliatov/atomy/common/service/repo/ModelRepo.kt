package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel

interface ModelRepo<Model : WithModel<Model>> :
  CommonRepo<Id<Model>, Model>,
  ReadOnlyModelRepo<Model>,
  WriteOnlyModelRepo<Model>

@Suppress("unused")
interface ReadOnlyModelRepo<Model : WithModel<Model>> :
  ReadOnlyCommonRepo<Id<Model>, Model>,
  WithModelToId<Model>

@Suppress("unused")
interface WriteOnlyModelRepo<Model : WithModel<Model>> :
  WriteOnlyCommonRepo<Id<Model>, Model>,
  WithModelToId<Model>

interface WithModelToId<Model : WithModel<Model>> : WithEntityToId<Id<Model>, Model> {
  override fun Model.id(): Id<Model>? = id
}