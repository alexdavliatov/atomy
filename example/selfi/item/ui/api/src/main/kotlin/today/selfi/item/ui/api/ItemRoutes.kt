package today.selfi.item.ui.api

import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import ru.adavliatov.atome.common.type.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.ref.*
import ru.adavliatov.atomy.common.type.ref.imp.json.*
import ru.adavliatov.common.type.json.impl.*
import today.selfie.domain.Item
import today.selfie.domain.JsonNodeBuilders.node
import today.selfie.item.service.repo.ItemRepo

class ItemRoutes(val itemRepo: ItemRepo) : CrudHandler {
  override fun create(ctx: Context) {
    TODO("not implemented")
  }

  override fun delete(ctx: Context, resourceId: String) {
    TODO("not implemented")
  }

  override fun getAll(ctx: Context) {
    val clientId = ctx.header("clientId") ?: throw InvalidArgumentError(message = "No client id provided")
    ctx.json(itemRepo.findAll())
  }

  override fun getOne(ctx: Context, resourceId: String) {
    val clientId = ctx.header("clientId") ?: throw InvalidArgumentError(message = "No client id provided")
    val rawId = ctx.pathParam("id").toLong()
    val id = Id.newId<Item>(
      Ref(
        JsonConsumerId(
          JacksonJson(
            node().with("id", clientId).end()!!
          )
        )
      )
    ).withId(rawId)
    ctx.json(itemRepo.findByIdChecked(id))
  }

  override fun update(ctx: Context, resourceId: String) {
    TODO("not implemented")
  }
}
