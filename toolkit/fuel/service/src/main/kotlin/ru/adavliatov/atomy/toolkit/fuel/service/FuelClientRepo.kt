package ru.adavliatov.atomy.toolkit.fuel.service

import ru.adavliatov.atomy.common.service.repo.CommonRepoAndHolder
import ru.adavliatov.atomy.common.service.repo.WithEntityToId
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.type.json.WithJsonContext
import ru.adavliatov.atomy.common.ui.api.domain.PrincipalRolesAuth
import ru.adavliatov.atomy.toolkit.fuel.service.behaviour.WithFuelChunkedAndHolder
import ru.adavliatov.atomy.toolkit.fuel.service.behaviour.WithFuelCreateAndHolder
import ru.adavliatov.atomy.toolkit.fuel.service.behaviour.WithFuelFetchOrCreateAndHolder
import ru.adavliatov.atomy.toolkit.fuel.service.behaviour.WithFuelFindByIdsAndHolder
import ru.adavliatov.atomy.toolkit.fuel.service.behaviour.WithFuelModifyAndHolder
import ru.adavliatov.atomy.toolkit.fuel.service.behaviour.WithFuelRemoveByIdsAndHolder
import ru.adavliatov.atomy.toolkit.fuel.service.behaviour.WithFuelUrl

interface FuelClientRepo<Holder : WithHolder, Id : Any, View : Any> :
  WithEntityToId<Id, View>,
  CommonRepoAndHolder<Holder, Id, View>,
  WithAuthToHolder<Holder, PrincipalRolesAuth>,
  WithFuelCreateAndHolder<Holder, Id, View>,
  WithFuelModifyAndHolder<Holder, Id, View>,
  WithFuelFetchOrCreateAndHolder<Holder, View>,
  WithFuelFindByIdsAndHolder<Holder, Id, View>,
  WithFuelChunkedAndHolder<Holder, View>,
  WithFuelRemoveByIdsAndHolder<Holder, Id, View>,
  WithFuelUrl,
  WithJsonContext