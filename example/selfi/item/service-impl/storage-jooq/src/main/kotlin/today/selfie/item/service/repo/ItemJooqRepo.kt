package today.selfie.item.service.repo

import org.jooq.SQLDialect.POSTGRES_10
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.name.*
import ru.adavliatov.atomy.common.type.ref.*
import ru.adavliatov.atomy.common.type.ref.impl.json.*
import ru.adavliatov.atomy.example.selfie.item.service.repo.generated.tables.daos.*
import ru.adavliatov.atomy.example.selfie.item.service.repo.generated.tables.pojos.*
import ru.adavliatov.atomy.example.selfie.item.service.repo.generated.tables.records.*
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.*
import ru.adavliatov.common.type.json.impl.*
import today.selfie.item.domain.Item
import today.selfie.item.domain.MissingDetails
import javax.sql.DataSource

open class ItemJooqRepo(ds: DataSource) : ModelJooqDaoAdapter<Item, ItemsRecord, Items>(ds),
  ItemRepo {
  override val dao: ItemsDao = ItemsDao(ds.toJooqConfig().set(POSTGRES_10))

  override val entityClass: Class<Item> = Item::class.java
  override val pojoClass: Class<Items> = Items::class.java

  override fun Item.toPojo(): Items = Items(
    id.checkedId,
    id.uid,
    id.ref.consumer.let { it as JsonConsumerId<*> }.let { it.value as JacksonJson },
    id.ref.ref?.let { it as JsonConsumerId<*> }?.let { it.value as JacksonJson },
    state.name,
    createdAt,
    modifiedAt,
    name.value,
    null,
    ownerId,
    null
  )

  override fun Items.toModel(): Item = Item(
    Id(
      id,
      uid,
      Ref(
        JsonConsumerId(consumer),
        JsonConsumerRef(ref)
      ),
      Item::class
    ),
    State(state),
    createdAt,
    modifiedAt,
    NameValue(name),
    null,
    ownerId,
    MissingDetails
  )
}