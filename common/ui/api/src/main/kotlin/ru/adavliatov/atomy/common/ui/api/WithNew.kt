package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes

interface WithNew<Id, View> {
  fun new(auth: Auth, view: View): IdWrapper<Id>

  fun Auth.canCreate(): Boolean = false

  fun newRoute(
    context: Context,
    request: View
  ): IdWrapper<Id> {
    val (_, response, auth) = context
    if (!auth.canCreate()) throw PermissionDeniedError(
      AccessErrorCodes.CanNotCreate
    )

    return new(auth, request)
      .also { response.withStatusCode(StatusCodes.CREATED) }
  }

}