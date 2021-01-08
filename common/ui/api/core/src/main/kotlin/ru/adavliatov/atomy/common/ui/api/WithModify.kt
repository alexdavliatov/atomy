package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.WithAuth
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes.CanNotModify
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.NO_CONTENT

interface WithModify<Id, View> {
  fun modify(auth: WithAuth, id: Id, view: View)

  fun WithAuth.canModify(modelId: Id): Boolean = false

  fun modifyRoute(
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