package ru.adavliatov.atomy.common.ui.api.ext

import ru.adavliatov.atomy.common.service.repo.OwnerHolder
import ru.adavliatov.atomy.common.ui.api.domain.PrincipalRolesAuth

object PrincipalAuthExtensions {
  fun PrincipalRolesAuth.toHolder() = OwnerHolder(consumer, principalId)
  fun OwnerHolder.toAuth() = PrincipalRolesAuth(
    consumer,
    ownerId
  )
}