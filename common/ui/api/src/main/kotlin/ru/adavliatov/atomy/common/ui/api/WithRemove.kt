package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes

interface WithRemove<Id> {
  fun remove(auth: Auth, id: Id)

  fun Auth.canRemove(modelId: Id): Boolean = false

  fun remove(
    context: Context,
    id: Id
  ) {
    val (_, response, auth) = context
    if (!auth.canRemove(id)) throw PermissionDeniedError(
      AccessErrorCodes.CanNotRemove
    )

    remove(auth, id)
      .also { response.withStatusCode(StatusCodes.NO_CONTENT) }
  }
}