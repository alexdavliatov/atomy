package ru.adavliatov.atomy.toolkit.jooq.service.behaviour

import org.jooq.Constraint
import org.jooq.Field
import org.jooq.JSONB
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.service.repo.WithFetchOrCreate
import ru.adavliatov.atomy.common.service.repo.WithFetchOrCreateAndHolder
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.toolkit.jooq.service.WithConsumerField
import ru.adavliatov.atomy.toolkit.jooq.service.WithField
import ru.adavliatov.atomy.toolkit.jooq.service.WithIdField
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqDao
import ru.adavliatov.atomy.toolkit.jooq.service.WithModelToPojo
import ru.adavliatov.atomy.toolkit.jooq.service.WithUidField

interface WithJooqFetchOrCreate<
    Model,
    Record : TableRecord<Record>,
    Pojo> : WithIdField<Record>,
  WithJooqDao<Model, Record, Pojo>,
  WithConsumerField<Record>,
  WithUidField<Record>,
  WithField<JSONB>,
  WithFetchOrCreate<Model>,
  WithModelToPojo<Model, Pojo> {
  override val specificField: Field<JSONB>
    get() = consumerField.value

  val insertOnDuplicateIgnoreConstraint: Constraint

  val fields: Lazy<List<Field<*>>>
    get() = lazy { table.fields().filterNot { idField.value == it || uidField.value == it } }

  //can not just ignore:
  //https://stackoverflow.com/questions/34708509/how-to-use-returning-with-on-conflict-in-postgresql/42217872#42217872
  override fun fetchOrCreate(item: Model): Model {
    val record = dsl.newRecord(table, item.toPojo())
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
interface WithJooqFetchOrCreateAndHolder<
    Holder: WithHolder,
    Model,
    Record : TableRecord<Record>,
    Pojo> : WithIdField<Record>,
  WithJooqDao<Model, Record, Pojo>,
  WithConsumerField<Record>,
  WithUidField<Record>,
  WithField<JSONB>,
  WithFetchOrCreateAndHolder<Holder, Model>,
  WithModelToPojo<Model, Pojo> {
  override val specificField: Field<JSONB>
    get() = consumerField.value

  val insertOnDuplicateIgnoreConstraint: Constraint

  val fields: Lazy<List<Field<*>>>
    get() = lazy { table.fields().filterNot { idField.value == it || uidField.value == it } }

  //can not just ignore:
  //https://stackoverflow.com/questions/34708509/how-to-use-returning-with-on-conflict-in-postgresql/42217872#42217872
  //fixme: introduce batch insert
  override fun fetchOrCreate(holder: Holder, item: Model): Model {
    val record = dsl.newRecord(table, item.toPojo())
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
