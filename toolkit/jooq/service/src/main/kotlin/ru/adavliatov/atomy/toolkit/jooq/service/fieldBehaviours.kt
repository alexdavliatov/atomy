package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.Field
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.*
import java.util.*

interface WithEntityToPojo<Entity : WithEntity<Entity>, Pojo> {
  val entityClass: Class<Entity>
  val pojoClass: Class<Pojo>

  fun Entity.toPojo(): Pojo
  fun Pojo.toEntity(): Entity
}

interface WithIdField<Record : TableRecord<Record>> {
  val table: Table<Record>

  val idField: Lazy<Field<Long>>
    get() = lazy { table.field("id", Long::class.java) }
}

interface WithUidIdField<Record : TableRecord<Record>> {
  val table: Table<Record>

  val uidField: Lazy<Field<UUID>>
    get() = lazy { table.field("uid", UUID::class.java) }
}

interface WithClientIdField<Record : TableRecord<Record>> {
  val table: Table<Record>

  val clientIdField: Lazy<Field<String>>
    get() = lazy { table.field("client_id", String::class.java) }
}

interface WithField<FieldType> {
  val specificField: Field<FieldType>
}
