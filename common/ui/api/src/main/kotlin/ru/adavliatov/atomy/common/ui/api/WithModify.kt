package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes

interface WithModify<Id, View> {
  fun modify(auth: Auth, id: Id, view: View)

  fun Auth.canModify(modelId: Id): Boolean = false

  fun modify(
    context: Context,
    id: Id,
    view: View
  ) {
    val (_, response, auth) = context
    if (!auth.canModify(id)) throw PermissionDeniedError(
      AccessErrorCodes.CanNotModify
    )

    modify(auth, id, view)
      .also { response.withStatusCode(StatusCodes.NO_CONTENT) }
  }
}