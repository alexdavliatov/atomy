package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes.CanNotCreate
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.CREATED

interface WithNew<Id, View> {
  fun new(auth: Auth, view: View): IdWrapper<Id>

  fun Auth.canCreate(): Boolean = false

  fun newRoute(
    context: Context,
    request: View
  ): Response {
    val (_, response, auth) = context
    if (!auth.canCreate()) throw PermissionDeniedError(CanNotCreate)

    return response
      .withStatusCode(CREATED)
      .withBody(new(auth, request))
  }

}