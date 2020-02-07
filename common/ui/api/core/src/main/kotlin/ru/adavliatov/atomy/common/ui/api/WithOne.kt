package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes.CanNotAccess
import ru.adavliatov.atomy.common.ui.api.domain.error.NotFoundError
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError

interface WithOne<Id, Model, View> : WithViewableModel<Model, View>,
  WithPropertyProjector<View> {
  fun one(auth: Auth, id: Id): Model?

  fun Auth.canAccess(modelId: Id): Boolean = false

  fun oneRoute(
    context: Context,
    id: Id,
    properties: List<Prop>?
  ): Response {
    val (_, response, auth) = context
    if (!auth.canAccess(id)) throw PermissionDeniedError(CanNotAccess)
    val model = one(auth, id) ?: throw NotFoundError("Resource with $id not found")

    return response
      .withBody(
        model
          .toView()
          .let { propertyProjector.project(it, properties?.toSet()) }
      )
  }
}