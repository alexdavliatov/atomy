package today.selfi.auth.ui.api

import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.common.type.json.impl.builder.JsonNodeBuilders.node
import today.selfi.item.domain.Item
import today.selfi.item.service.repo.ItemRepo
import today.selfi.shared.type.ref.ext.RefExtensions.ref

class AuthRoutes(val itemRepo: ItemRepo) : CrudHandler {
  override fun create(ctx: Context) {
    TODO("not implemented")
  }

  override fun delete(ctx: Context, resourceId: String) {
    TODO("not implemented")
  }

  override fun getAll(ctx: Context) {
    @Suppress("UNUSED_VARIABLE")
    val clientId = ctx.header("clientId") ?: throw InvalidArgumentError(message = "No client id provided")
    ctx.json(itemRepo.findAll())
  }

  override fun getOne(ctx: Context, resourceId: String) {
    val clientId = ctx.header("clientId") ?: throw InvalidArgumentError(message = "No client id provided")
    val rawId = ctx.pathParam("id").toLong()
    val id = Id.newId<Item>(
      ref(node().with("id", clientId).end()!!)
    ).withId(rawId)
    ctx.json(itemRepo.findByIdChecked(id))
  }

  override fun update(ctx: Context, resourceId: String) {
    TODO("not implemented")
  }
}
