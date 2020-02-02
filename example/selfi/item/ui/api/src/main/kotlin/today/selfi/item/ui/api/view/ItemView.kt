package today.selfi.item.ui.api.view

import com.fasterxml.jackson.databind.node.TextNode
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.name.*
import ru.adavliatov.atomy.common.type.ref.*
import today.selfi.item.domain.Item
import today.selfi.item.domain.ItemDsl.item
import today.selfi.item.domain.OwnerId
import today.selfi.item.service.repo.ItemRepo
import today.selfi.shared.type.ref.ext.RefExtensions.ref
import java.util.*

data class ItemView(
  val name: String?
) {
  fun toModel(ownerId: OwnerId, consumer: ConsumerId): Item = item {
    this.id = Id.randomIdWith(Ref(consumer = consumer))
    this@ItemView.name?.let { name = NameValue(it) }
    this.ownerId = ownerId
  }

  fun toModel(
    uid: UUID?,
    consumer: ConsumerId
  ): (ItemRepo) -> Item? = { repo ->
    val id = Id
      .newId<Item>(Ref(consumer))
      .withUid(uid)

    repo.findById(id)
  }

  fun id(clientId: String?): Id<Item> = Id
    .newId(ref(TextNode.valueOf(clientId)))

  fun id(clientId: String?, uid: UUID): Id<Item> = Id
    .newId<Item>(ref(TextNode.valueOf(clientId)))
    .withUid(uid)

  companion object {
    fun <M : WithModel<M>> Id<M>.withUid(uid: UUID?) = uid?.let { withUid(it) } ?: this
  }
}