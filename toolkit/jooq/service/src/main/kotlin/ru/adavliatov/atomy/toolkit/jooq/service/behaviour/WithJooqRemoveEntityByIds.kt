package ru.adavliatov.atomy.toolkit.jooq.service.behaviour

import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedIds
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedUids
import ru.adavliatov.atomy.common.service.repo.WithRemoveByIds
import ru.adavliatov.atomy.toolkit.jooq.service.WithDsl
import ru.adavliatov.atomy.toolkit.jooq.service.WithIdField
import ru.adavliatov.atomy.toolkit.jooq.service.WithTable
import ru.adavliatov.atomy.toolkit.jooq.service.WithUidField

@Suppress("unused")
interface WithJooqRemoveEntityByIds<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>
    > : WithDsl,
  WithTable<Record>,
  WithIdField<Record>,
  WithUidField<Record>,
  WithRemoveByIds<Entity> {
  override fun removeByIds(ids: Iterable<Id<Entity>>) {
    val (withId, withoutId) = ids.partition { it.id != null }
    val (withUid, _) = withoutId.partition { it.uid != null }
    //todo adavliatov: add remove by refs

    dsl
      .deleteFrom(table)
      .where(
        idField.value.`in`(withId.checkedIds().toSet())
          .or(uidField.value.`in`(withUid.checkedUids().toSet()))
      )
      .execute()
  }
}