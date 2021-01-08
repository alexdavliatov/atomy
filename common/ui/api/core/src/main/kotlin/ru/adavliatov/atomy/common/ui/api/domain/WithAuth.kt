package ru.adavliatov.atomy.common.ui.api.domain

import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.ui.api.auth.Role
import java.util.UUID

/**
 * Abstraction over user
 */
interface WithAuth

interface RoleAuth : WithAuth {
  val roles: Set<Role>
}

data class ConsumerAuth(
  val consumer: ConsumerId,
  override val roles: Set<Role> = setOf()
) : RoleAuth

data class PrincipalRolesAuth(
  val consumer: ConsumerId,
  val principalId: PrincipalId,
  override val roles: Set<Role> = setOf()
) : RoleAuth

typealias PrincipalId = UUID

object MissingAuth : RoleAuth {
  override val roles: Set<Role> = setOf()
}