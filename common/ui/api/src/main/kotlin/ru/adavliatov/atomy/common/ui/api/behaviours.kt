package ru.adavliatov.atomy.common.ui.api

import kotlin.reflect.KClass

interface WithNew<Id, View> {
  fun new(user: User, view: View): IdWrapper<Id>

  fun User.canCreate(): Boolean = false

  @ApiOperation("Create entity")
  @PostMapping(value = [""])
  fun new(
    @ModelAttribute user: User,
    @Valid @RequestBody request: View,
    response: HttpServletResponse
  ): IdWrapper<Id> {
    if (!user.canCreate()) throw PermissionDeniedError(CanNotCreate)

    return new(user, request).also { response.status = CREATED.value() }
  }

}

interface WithModify<Id, View> {
  fun modify(user: User, id: Id, view: View)

  fun User.canModify(modelId: Id): Boolean = false

  @ApiOperation("Update entity")
  @PatchMapping(value = ["{entityId}"])
  fun modify(
    @ModelAttribute user: User,
    @PathVariable(value = "entityId") id: Id,
    @RequestBody view: View,
    response: HttpServletResponse
  ) {
    if (!user.canModify(id)) throw PermissionDeniedError(CanNotModify)

    modify(user, id, view).also { response.status = NO_CONTENT.value() }
  }
}

interface WithRemove<Id> {
  fun remove(user: User, id: Id)

  fun User.canRemove(modelId: Id): Boolean = false

  @ApiOperation("Remove entity")
  @DeleteMapping(value = ["{entityId}"])
  fun remove(
    @ModelAttribute user: User,
    @PathVariable(value = "entityId") id: Id,
    response: HttpServletResponse
  ) {
    if (!user.canRemove(id)) throw PermissionDeniedError(CanNotRemove)

    remove(user, id).also { response.status = NO_CONTENT.value() }
  }
}

interface WithOne<Id, Model, View> : WithViewableModel<Model, View>, WithPropertyProjector<View> {
  fun one(user: User, id: Id): Model?

  fun User.canAccess(modelId: Id): Boolean = false

  @ApiOperation("Get entity")
  @GetMapping(value = ["{entityId}"])
  fun oneRoute(
    @ModelAttribute user: User,
    @PathVariable(value = "entityId") id: Id,
    @RequestParam(name = "fields", required = false) properties: List<String>?
  ): Any? {
    if (!user.canAccess(id)) throw PermissionDeniedError(CanNotAccess)
    val model = one(user, id) ?: throw NotFoundError()

    return model.toView().let { propertyProjector.project(it, properties?.toSet()) }
  }
}

interface WithPaginated<Model, View> : WithViewableModel<Model, View>, WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun all(user: User, chunk: Chunk): PageWithData<Model>

  @ApiOperation("Paginated entities")
  @GetMapping(value = [""])
  fun allRoute(@ModelAttribute user: User, @ModelAttribute chunk: Chunk) =
    ListViewResponse(
      all(user, chunk),
      chunk,
      toView(),
      { propertyExtractor.extractProperty(it, chunk.sortBy) },
      { propertyProjector.project(it, chunk.properties) }
    )
}

interface WithMultiple<Id, Model, View> : WithViewableModel<Model, View>, WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun multiple(user: User, ids: List<Id>, chunk: Chunk): PageWithData<Model>

  @ApiOperation("Paginated entities")
  @GetMapping(value = ["multiple"])
  fun multipleRoute(
    @ModelAttribute user: User,
    @ModelAttribute chunk: Chunk,
    @RequestParam(name = "ids", required = false) ids: List<Id>?
  ) =
    ListViewResponse(
      multiple(user, ids ?: listOf(), chunk),
      chunk,
      toView(),
      { propertyExtractor.extractProperty(it, chunk.sortBy) },
      { propertyProjector.project(it, chunk.properties) }
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

interface WithUserMeta {
  val securityService: SecurityService

  fun User.isSuper() = hasSuperRole()(securityService)

  fun <T> T.validateAccess(user: User, userIdProducer: T.() -> Long): T = apply {
    validate({ user.isSuper() || user.id == userIdProducer(this) }, PermissionDeniedError(CanNotAccess))
  }
}

open class IdWrapper<Id>(val id: Id)
