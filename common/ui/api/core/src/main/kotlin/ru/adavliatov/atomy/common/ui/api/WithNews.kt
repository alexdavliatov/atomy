package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.WithAuth
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes.CanNotCreate
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.CREATED
import ru.adavliatov.atomy.common.view.IdWrapper

interface WithNews<Id, View> {
  fun news(auth: WithAuth, views: List<View>): List<IdWrapper<Id>>

  fun WithAuth.canCreateMultiple(): Boolean = false

  fun newsRoute(
    context: Context,
    request: List<View>
  ): Response {
    val (_, response, auth) = context
    if (!auth.canCreateMultiple()) throw PermissionDeniedError(CanNotCreate)

    val ids = news(auth, request)

    return response
      .withStatusCode(CREATED)
      .withBody(ids.map { it.id })
  }

}