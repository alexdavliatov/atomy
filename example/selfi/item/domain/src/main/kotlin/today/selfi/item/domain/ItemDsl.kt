package today.selfi.item.domain

import com.fasterxml.jackson.databind.node.TextNode
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.type.name.NameValue
import today.selfi.shared.type.ref.ext.RefExtensions.ref
import java.time.Instant
import java.util.UUID

object ItemDsl {
  fun item(builder: ItemBuilder = ItemBuilder(), body: ItemBuilder.() -> Unit) = builder.apply(body).build()
}

//todo (adavliatov): introduce common builder (*Suit)
class ItemBuilder {
  var id: Id<Item> = Id.randomIdWith(
    ref = ref(TextNode(aRandomString()))
  )

  var state = State("active")
  var createdAt = Instant.now()
  var modifiedAt = createdAt

  var name: NameValue = NameValue("item")
  var duration = null
  var ownerId = 0L
  var details: ItemDetails = MissingDetails

  fun build(): Item = Item(
    id, state, createdAt, modifiedAt, name, duration, ownerId, details
  )

  companion object {
    private fun aRandomString() = UUID.randomUUID().toString()
  }
}

