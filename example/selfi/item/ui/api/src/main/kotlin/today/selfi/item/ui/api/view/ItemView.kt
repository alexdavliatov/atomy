package today.selfi.item.ui.api.view

import com.fasterxml.jackson.databind.JsonNode
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.type.json.impl.JacksonContext
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson
import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.Ref
import today.selfi.item.domain.Item
import today.selfi.item.domain.ItemDetails
import today.selfi.item.domain.ItemDetails.Companion.convertTo
import today.selfi.item.domain.ItemDsl.item
import today.selfi.item.domain.ItemTitle
import today.selfi.item.domain.OwnerId
import today.selfi.item.service.repo.ItemRepo
import java.util.UUID

data class ItemView(
  val name: String?,
  private val jsonDetails: JsonNode?
) {
  val details: JacksonJson?
    get() = jsonDetails?.run(::JacksonJson)

  fun toModel(ownerId: OwnerId, consumer: ConsumerId): Item = item {
    this.id = Id.randomIdWith(Ref(consumer = consumer))
    this@ItemView.name?.let { name = NameValue(it) }
    this.ownerId = ownerId
  }

  fun details(): (JacksonContext) -> ItemDetails? = { context ->
    val title = details
      ?.value("title", String::class.java)
      ?.invoke(context)
      ?.let { ItemTitle(NameValue(it)) }

    details
      ?.convertTo(title)
      ?.invoke(context)
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