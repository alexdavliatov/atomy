package today.selfi.item.ui.api

import io.javalin.http.Context
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
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

  override fun Auth.canCreate(): Boolean = true
  override fun Auth.canAccess(modelId: UUID) = true
  override fun Auth.canModify(modelId: UUID) = true
  override fun Auth.canRemove(modelId: UUID) = true
  override fun Auth.canCreateMultiple() = true

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

  override fun modify(auth: Auth, id: UUID, view: ItemView) {
    val (consumer, _) = auth as ConsumerWithZeroOwnerAuth
    val modelId = Id.newId<Item>(Ref(consumer)).withUid(id)

    itemRepo.findById(modelId)
      ?.run { view.toModel(id, consumer)(itemRepo) }
      ?.run {
        itemRepo.modify(this)
      }
  }

  override fun remove(auth: Auth, id: UUID) {
    val (consumer, _) = auth as ConsumerWithZeroOwnerAuth
    val modelId = Id.newId<Item>(Ref(consumer)).withUid(id)

    itemRepo
      .findById(modelId)
      ?.run { itemRepo.remove(this) }
  }

  override fun one(auth: Auth, id: UUID): Item? {
    val (consumer, _) = auth as ConsumerWithZeroOwnerAuth
    val modelId = Id.newId<Item>(Ref(consumer)).withUid(id)

    return itemRepo
      .findByIdChecked(modelId)
  }

  override fun multiple(auth: Auth, ids: List<UUID>, page: Page): ChunkedData<Item> {
    val (consumer, _) = auth as ConsumerWithZeroOwnerAuth

    return ids
      .map { Id.newId<Item>(Ref(consumer)).withUid(it) }
      .let { itemRepo.findByIds(it) }
      .let { ChunkedData(Chunk(0, it.size), it.toList()) }
  }

  override fun paginated(auth: Auth, page: Page): ChunkedData<Item> {
    val (_, _) = auth as ConsumerWithZeroOwnerAuth

    val chunk = Chunk(page.limit, page.offset)
    return itemRepo.findPaginated(chunk)
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
