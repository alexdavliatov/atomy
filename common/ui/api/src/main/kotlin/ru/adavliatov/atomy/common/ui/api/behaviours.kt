package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.*
import ru.adavliatov.atomy.common.type.page.*
import ru.adavliatov.atomy.common.ui.api.domain.*
import ru.adavliatov.atomy.common.ui.api.domain.error.*
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.CREATED
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.NO_CONTENT
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCodes.OK
import ru.yandex.contest.web.api.priv.admin.v1.PropertyExtractor
import ru.yandex.contest.web.api.priv.admin.v1.PropertyProjector
import kotlin.reflect.KClass

interface WithNew<Id, View> {
  fun new(auth: Auth, view: View): IdWrapper<Id>

  fun Auth.canCreate(): Boolean = false

  fun newRoute(
    context: Context,
    request: View
  ): IdWrapper<Id> {
    val (_, response, auth) = context
    if (!auth.canCreate()) throw PermissionDeniedError(CanNotCreate)

    return new(auth, request)
      .also { response.withStatusCode(CREATED) }
  }

}

interface WithModify<Id, View> {
  fun modify(auth: Auth, id: Id, view: View)

  fun Auth.canModify(modelId: Id): Boolean = false

  fun modify(
    context: Context,
    id: Id,
    view: View
  ) {
    val (_, response, auth) = context
    if (!auth.canModify(id)) throw PermissionDeniedError(CanNotModify)

    modify(auth, id, view)
      .also { response.withStatusCode(NO_CONTENT) }
  }
}

interface WithRemove<Id> {
  fun remove(auth: Auth, id: Id)

  fun Auth.canRemove(modelId: Id): Boolean = false

  fun remove(
    context: Context,
    id: Id
  ) {
    val (_, response, auth) = context
    if (!auth.canRemove(id)) throw PermissionDeniedError(CanNotRemove)

    remove(auth, id)
      .also { response.withStatusCode(NO_CONTENT) }
  }
}

typealias FieldName = String
interface WithOne<Id, Model, View> : WithViewableModel<Model, View>, WithPropertyProjector<View> {
  fun one(auth: Auth, id: Id): Model?

  fun Auth.canAccess(modelId: Id): Boolean = false

  fun oneRoute(
    context: Context,
    id: Id,
    properties: List<FieldName>?
  ): Any? {
    val (_, response, auth) = context
    if (!auth.canAccess(id)) throw PermissionDeniedError(CanNotAccess)
    val model = one(auth, id) ?: throw NotFoundError()

    return model.toView()
      .let { propertyProjector.project(it, properties?.toSet()) }
      .also { response.withStatusCode(OK) }
  }
}

interface WithPaginated<Model, View : Any> :
  WithViewableModel<Model, View>,
  WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun all(auth: Auth, page: Page): Chunk<Model>

  fun allRoute(
    context: Context,
    page: Page
  ) {
    val (_, response, auth) = context

    ListViewResponse(
      all(auth, page),
      page,
      toView(),
      { propertyExtractor.extractProperty(it, page.sortBy) },
      { propertyProjector.project(it, page.properties) }
    )
  }
}

interface WithMultiple<Id, Model, View : Any> : WithViewableModel<Model, View>, WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun multiple(auth: Auth, ids: List<Id>, page: Page): Chunk<Model>

  fun multipleRoute(
    auth: Auth,
    page: Page,
    ids: List<Id>?
  ) =
    ListViewResponse(
      multiple(auth, ids ?: listOf(), page),
      page,
      toView(),
      { propertyExtractor.extractProperty(it, page.sortBy) },
      { propertyProjector.project(it, page.properties) }
    )
}

interface WithViewableModel<Model, View> {
  fun Model.toView(): View
  fun toView(): (Model) -> View = { it.toView() }
}

interface WithPropertyExtractor<View> {
  val propertyExtractor: PropertyExtractor<View>
}

interface WithPropertyProjector<View> {
  val propertyProjector: PropertyProjector<View>
}

abstract class WithPropertyHandler<View : Any>(klass: KClass<View>) : WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  override val propertyExtractor: PropertyExtractor<View> = PropertyExtractor(klass)
  override val propertyProjector: PropertyProjector<View> = PropertyProjector(klass)
}

open class IdWrapper<Id>(val id: Id)
