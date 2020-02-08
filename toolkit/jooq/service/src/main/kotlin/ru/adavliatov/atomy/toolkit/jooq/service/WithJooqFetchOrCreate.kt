package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.Constraint
import org.jooq.Field
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.service.repo.WithFetchOrCreate
import java.util.UUID

interface WithJooqFetchOrCreate<
    Model,
    Record : TableRecord<Record>,
    Pojo> : WithIdField<Record>,
  WithJooqDao<Model, Record, Pojo>,
  WithUidField<Record>,
  WithField<UUID>,
  WithFetchOrCreate<Model>,
  WithModelToPojo<Model, Pojo> {
  override val specificField: Field<UUID>
    get() = uidField.value

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
