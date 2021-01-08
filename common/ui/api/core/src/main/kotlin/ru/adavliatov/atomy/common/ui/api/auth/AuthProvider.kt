package ru.adavliatov.atomy.common.ui.api.auth

import ru.adavliatov.atomy.common.ui.api.domain.Context

interface RoleAuthProvider {
  fun Context.roles(): Set<Role>
}

interface Role