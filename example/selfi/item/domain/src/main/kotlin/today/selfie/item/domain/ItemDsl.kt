package today.selfie.item.domain

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.name.*
import ru.adavliatov.atomy.common.type.ref.*
import ru.adavliatov.atomy.common.type.ref.imp.json.*
import ru.adavliatov.common.type.json.impl.*
import ru.adavliatov.common.type.json.impl.builder.JsonNodeBuilders.node
import java.time.Instant

object ItemDsl {
  fun item(builder: ItemBuilder = ItemBuilder(), body: ItemBuilder.() -> Unit) = builder.apply(body).build()
}

class ItemBuilder {
  val id: Id<Item> = Id.randomIdWith(
    Ref(
      JsonConsumerId(
        JacksonJson(
          node("id", "value").end()!!
        )
      )
    )
  )
  var state = State("active")
  var createdAt = Instant.now()
  var modifiedAt = createdAt

  var name = NameValue("item")
  var duration = null
  var ownerId = 0L
  var details = MissingDetails

  fun build(): Item = Item(
    id, state, createdAt, modifiedAt, name, duration, ownerId, details
  )
}

