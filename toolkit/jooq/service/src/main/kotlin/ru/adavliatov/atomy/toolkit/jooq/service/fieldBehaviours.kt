package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.Configuration
import org.jooq.DAO
import org.jooq.Field
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import java.util.*
import javax.sql.DataSource

interface WithJooqConfig {
  fun DataSource.toJooqConfig(): Configuration
}

interface WithTable<Record : TableRecord<Record>> {
  val table: Table<Record>
}

interface WithJooqDao<
    Entity,
    Record : TableRecord<Record>,
    Pojo> : WithTable<Record> {
  val dao: DAO<Record, Pojo, Long>
  override val table: Table<Record>
    get() = dao.table
}

interface WithEntityToPojo<Entity, Pojo> {
  val entityClass: Class<Entity>
  val pojoClass: Class<Pojo>

  fun Entity.toPojo(): Pojo
  fun Pojo.toEntity(): Entity

  fun Iterable<Entity>.toPojos(): Iterable<Pojo> = map { it.toPojo() }
  fun Iterable<Entity>.toPojoSet(): Set<Pojo> = mapToSet { it.toPojo() }

  fun Iterable<Pojo>.toEntities(): Iterable<Entity> = map { it.toEntity() }
  fun Iterable<Pojo>.toEntitySet(): Set<Entity> = mapToSet { it.toEntity() }
}

interface WithModelToPojo<Model, Pojo> :
  WithEntityToPojo<Model, Pojo> {
  override val entityClass: Class<Model>
  override val pojoClass: Class<Pojo>

  override fun Pojo.toEntity(): Model = toModel()

  fun Pojo.toModel(): Model

  fun Iterable<Pojo>.toModels(): Iterable<Model> = map { it.toModel() }
  fun Iterable<Pojo>.toModelSet(): Set<Model> = mapToSet { it.toModel() }
}

interface WithIdField<Record : TableRecord<Record>> : WithTable<Record> {
  val idField: Lazy<Field<Long>>
    get() = lazy { table.field("id", Long::class.java) }
}

interface WithUidIdField<Record : TableRecord<Record>> : WithTable<Record> {
  val uidField: Lazy<Field<UUID>>
    get() = lazy { table.field("uid", UUID::class.java) }
}

interface WithClientIdField<Record : TableRecord<Record>> : WithTable<Record> {
  val clientIdField: Lazy<Field<String>>
    get() = lazy { table.field("client_id", String::class.java) }
}

interface WithField<FieldType> {
  val specificField: Field<FieldType>
}
