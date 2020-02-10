package today.selfi.item.service.repo

import org.jooq.Constraint
import org.jooq.SQLDialect.POSTGRES_10
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.type.json.impl.JacksonContext
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson
import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.type.ref.Ref
import ru.adavliatov.atomy.common.type.ref.impl.json.JsonConsumerId
import ru.adavliatov.atomy.common.type.ref.impl.json.JsonConsumerRef
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.ModelJooqDaoAdapter
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqFetchOrCreate
import today.selfi.item.domain.Item
import today.selfi.item.domain.ItemDetails
import today.selfi.item.domain.MissingDetails
import today.selfi.item.service.repo.generated.Keys.ITEM_NAME_UNQ
import today.selfi.item.service.repo.generated.tables.daos.ItemsDao
import today.selfi.item.service.repo.generated.tables.pojos.Items
import today.selfi.item.service.repo.generated.tables.records.ItemsRecord
import javax.sql.DataSource

open class ItemJooqRepo(ds: DataSource, private val context: JacksonContext) :
  ModelJooqDaoAdapter<Item, ItemsRecord, Items>(ds),
  ItemRepo,
  WithJooqFetchOrCreate<Item, ItemsRecord, Items> {
  override val dao: ItemsDao = ItemsDao(ds.toJooqConfig().set(POSTGRES_10))

  override val entityClass: Class<Item> = Item::class.java
  override val pojoClass: Class<Items> = Items::class.java

  override val insertOnDuplicateIgnoreConstraint: Constraint
    //todo adavliatov: introduce meaningful constraint
    get() = ITEM_NAME_UNQ.constraint()

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
    context.toJson(details)
  )

  override fun Items.toModel(): Item = Item(
    Id(
      id,
      uid,
      Ref(
        JsonConsumerId(consumer),
        ref?.let { JsonConsumerRef(it) }
      ),
      entityClass
    ),
    State(state),
    createdAt,
    modifiedAt,
    NameValue(name),
    null,
    ownerId,
    details?.run { context.fromJson(details, ItemDetails::class.java) } ?: MissingDetails
  )
}