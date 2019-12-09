package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.Constraint
import org.jooq.DAO
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import ru.adavliatov.atomy.common.service.repo.*

@Suppress("unused")
interface WithModelToPojo<Model : WithModel<Model>, Pojo> :
  WithEntityToPojo<Model, Pojo> {
  override val entityClass: Class<Model>
  override val pojoClass: Class<Pojo>

  override fun Pojo.toEntity(): Model = toModel()

  fun Pojo.toModel(): Model
}

@Suppress("unused")
interface WithJooqFindByIds<
    Model : WithEntity<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithIdField<Record>,
  WithFindByIds<Model> {
  val dao: DAO<Record, Pojo, Long>
  override val table: Table<Record>

  fun Pojo.toModel(): Model

  override fun findByIds(ids: Iterable<Id<Model>>): Set<Model> = dao
    .fetch(
      idField.value,
      *ids.map { it.id }.toTypedArray()
    )
    .mapToSet { it.toModel() }
}

@Suppress("unused")
interface WithJooqFetchOrCreateModel<
    Model : WithEntity<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithIdField<Record>,
  WithClientIdField<Record>,
  WithJooqFetchOrCreate<String, Model, Record, Pojo> {
  override val specificField: Field<String>
    get() = clientIdField.value
}

interface WithJooqFetchOrCreate<
    FieldType,
    Model,
    Record : TableRecord<Record>,
    Pojo> : WithIdField<Record>,
  WithUidIdField<Record>,
  WithField<FieldType>,
  WithFetchOrCreate<Model> {
  val pojoClass: Class<Pojo>
  val dsl: DSLContext

  @Suppress("unused")
  val dao: DAO<Record, Pojo, Long>
  override val table: Table<Record>
  val insertOnDuplicateIgnoreConstraint: Constraint

  val fields: Lazy<List<Field<*>>>
    get() = lazy { table.fields().filterNot { idField.value == it || uidField.value == it || specificField == it } }

  fun Model.toPojo(): Pojo
  fun Pojo.toModel(): Model

  //can not just ignore:
  //see https://stackoverflow.com/questions/34708509/how-to-use-returning-with-on-conflict-in-postgresql/42217872#42217872
  override fun fetchOrCreate(model: Model): Model {
    val record = dsl.newRecord(table, model.toPojo())
    val values = fields.value.map { record[it] }

    return dsl
      .insertInto(table, fields.value)
      .values(values)
      .onConflictOnConstraint(insertOnDuplicateIgnoreConstraint)
      .doUpdate()
      .set(specificField, record.get(specificField))
      .returning()
      .apply { println(sql) }
      .fetchOne()
      .into(pojoClass)
      .toModel()
  }
}
