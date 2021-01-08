package ru.adavliatov.atomy.toolkit.fuel.service

import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.ui.api.domain.WithAuth

interface WithAuthToHolder<Holder : WithHolder, Auth : WithAuth> {
  fun Auth.toHeader(): String
  fun Holder.toHeader(): String
}