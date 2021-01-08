package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel

interface ModelRepoAndHolder<Holder : WithHolder, Model : WithModel<Model>> :
  CommonRepoAndHolder<Holder, Id<Model>, Model>,
  ReadOnlyModelRepoAndHolder<Holder, Model>,
  WriteOnlyModelRepoAndHolder<Holder, Model>

@Suppress("unused")
interface ReadOnlyModelRepoAndHolder<Holder : WithHolder, Model : WithModel<Model>> :
  ReadOnlyCommonRepoAndHolder<Holder, Id<Model>, Model>,
  WithModelToId<Model>

@Suppress("unused")
interface WriteOnlyModelRepoAndHolder<Holder : WithHolder, Model : WithModel<Model>> :
  WriteOnlyCommonRepoAndHolder<Holder, Id<Model>, Model>,
  WithModelToId<Model>