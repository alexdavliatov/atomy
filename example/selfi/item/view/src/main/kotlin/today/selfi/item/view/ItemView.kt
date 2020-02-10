package today.selfi.item.view

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
import today.selfi.item.view.ItemDetailsResolver.toDetails

data class ItemView(
  val name: String?,
  @JsonTypeInfo(use = NAME, include = PROPERTY, property = "title")
  val details: ItemDetailsView?
) {
  fun toModel(ownerId: OwnerId, consumer: ConsumerId): Item = item {
    id = Id.randomIdWith(Ref(consumer = consumer))
    this@ItemView.name?.let { name = NameValue(it) }
    this.ownerId = ownerId
    this@ItemView.details?.let { details = it.toDetails() }
  }
}