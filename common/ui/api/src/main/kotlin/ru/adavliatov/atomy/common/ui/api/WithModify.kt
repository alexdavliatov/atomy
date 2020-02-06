package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes.CanNotModify
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.NO_CONTENT

interface WithModify<Id, View> {
  fun modify(auth: Auth, id: Id, view: View)

  fun Auth.canModify(modelId: Id): Boolean = false

  fun modify(
    context: Context,
    id: Id,
    view: View
  ): Response {
    val (_, response, auth) = context
    if (!auth.canModify(id)) throw PermissionDeniedError(CanNotModify)

    modify(auth, id, view)
    return response.withStatusCode(NO_CONTENT)
  }
}