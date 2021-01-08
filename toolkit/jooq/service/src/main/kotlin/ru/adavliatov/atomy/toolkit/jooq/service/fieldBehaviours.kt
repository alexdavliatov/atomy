package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.Configuration
import org.jooq.DAO
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.JSONB
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.service.repo.WithOwnerHolder
import java.util.UUID
import javax.sql.DataSource

interface WithJooqConfig {
  fun DataSource.toJooqConfig(): Configuration
}

interface WithTable<Record : TableRecord<Record>> {
  val table: Table<Record>
}

interface WithJooqHolder<Holder : WithHolder, HolderRecord : TableRecord<HolderRecord>> : WithDSL {
  val holderTable: Table<HolderRecord>

  val holderIdField: Lazy<Field<Long>>
    get() = lazy { holderTable.field("id", Long::class.java) }
  val holderUidField: Lazy<Field<UUID>>
    get() = lazy { holderTable.field("uid", UUID::class.java) }
  val holderConsumerField: Lazy<Field<JSONB>>
    get() = lazy { holderTable.field("consumer", JSONB::class.java) }
  val holderOwnerField: Lazy<Field<UUID>>
    get() = lazy { holderTable.field("owner_id", UUID::class.java) }

  //fixme (adavliatov): introduce `consumer` join
  fun Holder.toCondition() = when (this) {
    is WithOwnerHolder -> holderTable.field(holderOwnerField.value).eq(ownerId)
    else -> org.jooq.impl.DSL.falseCondition()
  }

  fun <Record : TableRecord<Record>> Table<Record>.selectFromJoinHolder(ownerField: Field<Long>) = dsl
    .select()
    .from(this)
    .join(holderTable).on(
      holderTable.field(holderIdField.value)
        .eq(field(ownerField))
    )
}

interface WithDSL {
  val dsl: DSLContext
}

interface WithJooqDao<
    Model,
    Record : TableRecord<Record>,
    Pojo> : WithTable<Record>, WithDSL {
  val dao: DAO<Record, Pojo, Long>
  override val dsl: DSLContext
    get() = dao.configuration().dsl()
  override val table: Table<Record>
    get() = dao.table
}

interface WithModelToPojo<Model, Pojo> {
  val entityClass: Class<Model>
  val pojoClass: Class<Pojo>

  fun Model.toPojo(): Pojo
  fun Pojo.toEntity(): Model = toModel()

  fun Pojo.toModel(): Model

  fun Iterable<Pojo>.toModels(): Iterable<Model> = map { it.toModel() }
  fun Iterable<Pojo>.toModelSet(): Set<Model> = mapToSet { it.toModel() }

  fun Iterable<Model>.toPojos(): Iterable<Pojo> = map { it.toPojo() }
  fun Iterable<Model>.toPojoSet(): Set<Pojo> = mapToSet { it.toPojo() }

  fun Iterable<Pojo>.toEntities(): Iterable<Model> = map { it.toEntity() }
  fun Iterable<Pojo>.toEntitySet(): Set<Model> = mapToSet { it.toEntity() }
}

interface WithIdField<Record : TableRecord<Record>> : WithTable<Record> {
  val idField: Lazy<Field<Long>>
    get() = lazy { table.field("id", Long::class.java) }
}

interface WithUidField<Record : TableRecord<Record>> : WithTable<Record> {
  val uidField: Lazy<Field<UUID>>
    get() = lazy { table.field("uid", UUID::class.java) }
}

interface WithConsumerField<Record : TableRecord<Record>> : WithTable<Record> {
  val consumerField: Lazy<Field<JSONB>>
    get() = lazy { table.field("consumer", JSONB::class.java) }
}

interface WithStateField<Record : TableRecord<Record>> : WithTable<Record> {
  val stateField: Lazy<Field<String>>
    get() = lazy { table.field("state", String::class.java) }
}

interface WithOwnerField<Record : TableRecord<Record>> : WithTable<Record> {
  val ownerField: Lazy<Field<Long>>
    get() = lazy { table.field("owner_id", Long::class.java) }
}

interface WithField<FieldType> {
  val specificField: Field<FieldType>
}
