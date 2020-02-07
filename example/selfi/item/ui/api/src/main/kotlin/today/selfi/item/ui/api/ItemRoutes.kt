package today.selfi.item.ui.api

import io.javalin.http.Context
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.Ref
import ru.adavliatov.atomy.common.ui.api.IdWrapper
import ru.adavliatov.atomy.common.ui.api.WithPropertyHandler
import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.plugin.javalin.CommonJavalinController
import ru.adavliatov.atomy.common.ui.api.plugin.javalin.WithJavalinUUID
import ru.adavliatov.atomy.common.ui.api.plugin.javalin.WithJavalinUUIDs
import today.selfi.item.domain.Item
import today.selfi.item.service.repo.ItemRepo
import today.selfi.item.ui.api.view.ItemView
import today.selfi.shared.type.ref.ext.RefExtensions
import java.util.UUID

class ItemRoutes(private val itemRepo: ItemRepo) : CommonJavalinController<UUID, Item, ItemView>,
  WithJavalinUUID,
  WithJavalinUUIDs,
  WithPropertyHandler<ItemView>(ItemView::class) {

  override val viewClass = ItemView::class.java
  override fun Item.toView(): ItemView = ItemView(name.name)
  override fun Context.views(): Set<ItemView> = bodyAsClass(Array<ItemView>::class.java).toSet()
  override fun Context.auth(): ConsumerWithZeroOwnerAuth = ConsumerWithZeroOwnerAuth(consumer(), 0L)

  override fun new(auth: Auth, view: ItemView): IdWrapper<UUID> {
    val (consumer, owner) = auth as ConsumerWithZeroOwnerAuth
    val item: Item = itemRepo.fetchOrCreate(view.toModel(owner, consumer))

    return IdWrapper(item.checkedUid)
  }

  override fun news(auth: Auth, views: Set<ItemView>) {
    val (consumer, owner) = auth as ConsumerWithZeroOwnerAuth
    val items = views.map { it.toModel(owner, consumer) }

    itemRepo.fetchOrCreate(items)
  }

  override fun modify(auth: Auth, id: UUID, view: ItemView): Unit = TODO("not implemented")

  override fun one(auth: Auth, id: UUID): Item? = TODO("not implemented")

  override fun multiple(auth: Auth, ids: List<UUID>, page: Page): Chunk<Item> = TODO("not implemented")

  override fun paginated(auth: Auth, page: Page): Chunk<Item> = TODO("not implemented")

  fun delete(ctx: Context, resourceId: String) {
    val consumer = ctx.consumer()
    val resource = UUID.fromString(resourceId)
    val id = Id.newId<Item>(consumer).withUid(resource)

    itemRepo
      .findById(id)
      ?.run { itemRepo.remove(this) }
      ?.let { ctx.status(200) }
      ?: ctx.status(404)
  }

  fun getAll(ctx: Context) {
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

  fun getOne(ctx: Context, resourceId: String) {
    val consumer = ctx.consumer()
    val uid = UUID.fromString(resourceId)
    val id = Id.newId<Item>(Ref(consumer)).withUid(uid)
    ctx.json(itemRepo.findByIdChecked(id))
  }

  fun update(ctx: Context, resourceId: String) {
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

  data class ConsumerWithZeroOwnerAuth(
    val consumer: ConsumerId,
    val ownerId: Long
  ) : Auth
}
