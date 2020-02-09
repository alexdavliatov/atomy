package today.selfi.item.ui.api.view

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.Ref
import today.selfi.item.domain.Item
import today.selfi.item.domain.ItemDsl.item
import today.selfi.item.domain.OwnerId
import today.selfi.item.service.repo.ItemRepo
import java.util.UUID

data class ItemView(
  val name: String?,
  @JsonTypeInfo(use = NAME, include = PROPERTY, property = "title")
  private val details: ItemDetailsView?
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
}