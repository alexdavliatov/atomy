package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes.CanNotRemove
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.NO_CONTENT

interface WithRemove<Id> {
  fun remove(auth: Auth, id: Id)

  fun Auth.canRemove(modelId: Id): Boolean = false

  fun remove(
    context: Context,
    id: Id
  ): Response {
    val (_, response, auth) = context
    if (!auth.canRemove(id)) throw PermissionDeniedError(CanNotRemove)

    remove(auth, id)
    return response
      .withStatusCode(NO_CONTENT)
  }
}