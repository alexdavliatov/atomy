package ru.adavliatov.atomy.common.ui.api.plugin.javalin.auth

import ru.adavliatov.atomy.common.ui.api.auth.Role
import io.javalin.core.security.Role as JavalinRole

enum class ApiRole : Role, JavalinRole {
  anonymous,
  admin,
  developer,
  user;

  companion object {
    fun String.toApiRole() = when (this) {
      "admin" -> admin
      "developer" -> developer
      "user" -> user
      else -> anonymous
    }
  }
}