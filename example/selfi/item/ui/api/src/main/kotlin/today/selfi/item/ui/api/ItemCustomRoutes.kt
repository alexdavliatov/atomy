package today.selfi.item.ui.api

import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors
import ru.adavliatov.atomy.common.type.ref.Ref
import today.selfi.item.domain.Item
import today.selfi.item.service.repo.ItemRepo
import today.selfi.item.ui.api.view.ItemView
import today.selfi.shared.type.ref.ext.RefExtensions
import java.util.UUID

class ItemCustomRoutes(private val itemRepo: ItemRepo) : CrudHandler {
  override fun create(ctx: Context) {
    val consumer = ctx.consumer()
    val owner = 0L
    val views = ctx.bodyAsClass(Array<ItemView>::class.java).toList()
    val items = views.map { it.toModel(owner, consumer) }

    itemRepo
      .fetchOrCreate(items)
      .run { ctx.status(201).json(this) }
  }

  override fun delete(ctx: Context, resourceId: String) {
    val consumer = ctx.consumer()
    val resource = UUID.fromString(resourceId)
    val id = Id.newId<Item>(consumer).withUid(resource)

    itemRepo
      .findById(id)
      ?.run { itemRepo.remove(this) }
      ?.let { ctx.status(200) }
      ?: ctx.status(404)
  }

  override fun getAll(ctx: Context) {
//    val consumer = ctx.consumer()
    ctx.json(itemRepo.findAll())
  }

  fun multiple(ctx: Context) {
    val consumer = ctx.consumer()
    val uids = ctx.bodyAsClass(Array<UUID>::class.java).toList()
    uids
      .map {
        Id.newId<Item>(Ref(consumer)).withUid(it)
      }
      .let {
        ctx.json(itemRepo.findByIds(it))
      }
  }

  override fun getOne(ctx: Context, resourceId: String) {
    val consumer = ctx.consumer()
    val uid = UUID.fromString(resourceId)
    val id = Id.newId<Item>(Ref(consumer)).withUid(uid)
    ctx.json(itemRepo.findByIdChecked(id))
  }

  override fun update(ctx: Context, resourceId: String) {
    val consumer = ctx.consumer()
    val uid = UUID.fromString(resourceId)
    val id = Id.newId<Item>(Ref(consumer)).withUid(uid)
    val view = ctx.bodyAsClass(ItemView::class.java)
    itemRepo.findById(id)
      ?.run { view.toModel(uid, consumer)(itemRepo) }
      ?.run {
        itemRepo.modify(this)
        ctx.status(200)
      }
      ?: ctx.status(404)
  }

  companion object {
    fun Context.consumer() =
      header("consumer")
        ?.run { RefExtensions.consumer(this) }
        ?: throw HttpWrapperErrors.InvalidArgumentError(message = "No client id provided")
  }
}
