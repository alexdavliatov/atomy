package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.WithAuth
import ru.adavliatov.atomy.common.ui.api.domain.error.AccessErrorCodes.CanNotAccess
import ru.adavliatov.atomy.common.ui.api.domain.error.NotFoundError
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError

interface WithOne<Id, Model, View> : WithViewableModel<Model, View>,
  WithPropertyProjector<View> {
  fun one(auth: WithAuth, id: Id): Model?
  fun oneView(auth: WithAuth, id: Id): View?

  fun WithAuth.canAccess(modelId: Id): Boolean = false

  fun oneRoute(
    context: Context,
    id: Id,
    properties: List<Prop>?
  ): Response {
    val (_, response, auth) = context
    if (!auth.canAccess(id)) throw PermissionDeniedError(CanNotAccess)
//    val model = one(auth, id) ?: throw NotFoundError("Resource with $id not found")
    val view = oneView(auth, id) ?: throw NotFoundError("Resource with $id not found")

    return response
      .withBody(
        view
//        model
//          .toView()
          .let { propertyProjector.project(it, properties?.toSet()) }
      )
  }
}