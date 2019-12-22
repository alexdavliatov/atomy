package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.Constraint
import org.jooq.DAO
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedIdsToArray
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import ru.adavliatov.atomy.common.service.repo.*

@Suppress("unused")
interface WithJooqFindByIds<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Entity, Record, Pojo>,
  WithIdField<Record>,
  WithFindByIds<Entity> {
  override val dao: DAO<Record, Pojo, Long>

  fun Pojo.toModel(): Entity

  override fun findByIds(ids: Iterable<Id<Entity>>): Set<Entity> = dao
    .fetch(
      idField.value,
      *ids.checkedIdsToArray()
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
  WithJooqDao<Model, Record, Pojo>,
  WithUidIdField<Record>,
  WithField<FieldType>,
  WithFetchOrCreate<Model>,
  WithModelToPojo<Model, Pojo> {
  val dsl: DSLContext

  val insertOnDuplicateIgnoreConstraint: Constraint

  val fields: Lazy<List<Field<*>>>
    get() = lazy { table.fields().filterNot { idField.value == it || uidField.value == it || specificField == it } }

  //can not just ignore:
  //https://stackoverflow.com/questions/34708509/how-to-use-returning-with-on-conflict-in-postgresql/42217872#42217872
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
      .fetchOne()
      .into(pojoClass)
      .toModel()
  }
}
