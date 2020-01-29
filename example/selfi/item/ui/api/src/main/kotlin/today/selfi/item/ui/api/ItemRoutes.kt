package today.selfi.item.ui.api

import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.common.type.json.impl.builder.JsonNodeBuilders.node
import today.selfi.item.ui.api.view.ItemView
import today.selfi.shared.ref.ext.RefExtensions.ref
import today.selfie.item.domain.Item
import today.selfie.item.service.repo.ItemRepo
import java.util.UUID

class ItemRoutes(val itemRepo: ItemRepo) : CrudHandler {
  override fun create(ctx: Context) {
    val clientId = ctx.clientId()
    val views = ctx.bodyAsClass(Array<ItemView>::class.java).toList()
    val items = views.map { it.toModel(clientId) }

    itemRepo
      .fetchOrCreate(items)
      .run { ctx.json(this) }
  }

  override fun delete(ctx: Context, resourceId: String) {
    val clientId = ctx.clientId()
    val resource = UUID.fromString(resourceId)
    val id = Id.newId<Item>(
      ref(node().with("id", clientId).end()!!)
    ).withUid(resource)

    itemRepo
      .findById(id)
      ?.run { itemRepo.remove(this) }
      ?.let { ctx.status(200) }
      ?: ctx.status(404)
  }

  override fun getAll(ctx: Context) {
    val clientId = ctx.clientId()
    val uids = ctx.bodyAsClass(Array<UUID>::class.java).toList()
    uids
      .map {
        Id.newId<Item>(
          ref(node().with("id", clientId).end()!!)
        ).withUid(it)
      }
      .let {
        ctx.json(itemRepo.findByIds(it))
      }
  }

  override fun getOne(ctx: Context, resourceId: String) {
    val clientId = ctx.clientId()
    val rawId = ctx.pathParam("id").toLong()
    val id = Id.newId<Item>(
      ref(node().with("id", clientId).end()!!)
    ).withId(rawId)
    ctx.json(itemRepo.findByIdChecked(id))
  }

  override fun update(ctx: Context, resourceId: String) {
    TODO("not implemented")
  }

  companion object {
    fun Context.clientId() =
      header("clientId")// ?: throw HttpWrapperErrors.InvalidArgumentError(message = "No client id provided")
  }
}
