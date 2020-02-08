package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.Configuration
import org.jooq.DAO
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import java.util.UUID
import javax.sql.DataSource

interface WithJooqConfig {
  fun DataSource.toJooqConfig(): Configuration
}

interface WithTable<Record : TableRecord<Record>> {
  val table: Table<Record>
}

interface WithDsl {
  val dsl: DSLContext
}

interface WithJooqDao<
    Model,
    Record : TableRecord<Record>,
    Pojo> : WithTable<Record>, WithDsl {
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
  val consumerField: Lazy<Field<String>>
    get() = lazy { table.field("consumer", String::class.java) }
}

interface WithStateField<Record : TableRecord<Record>> : WithTable<Record> {
  val stateField: Lazy<Field<String>>
    get() = lazy { table.field("state", String::class.java) }
}

interface WithField<FieldType> {
  val specificField: Field<FieldType>
}
