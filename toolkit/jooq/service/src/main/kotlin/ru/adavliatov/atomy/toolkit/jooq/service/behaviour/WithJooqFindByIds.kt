package ru.adavliatov.atomy.toolkit.jooq.service.behaviour

import org.jooq.DAO
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedIds
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedUids
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import ru.adavliatov.atomy.common.service.repo.WithFindByIds
import ru.adavliatov.atomy.toolkit.jooq.service.WithIdField
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqDao
import ru.adavliatov.atomy.toolkit.jooq.service.WithModelToPojo
import ru.adavliatov.atomy.toolkit.jooq.service.WithUidField
import java.util.UUID

@Suppress("unused")
interface WithJooqFindByIds<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Model, Record, Pojo>,
  WithJooqFindByCheckedIds<Model, Record, Pojo>,
  WithJooqFindByCheckedUids<Model, Record, Pojo>,
  WithUidField<Record>,
  WithFindByIds<Model> {
  override val dao: DAO<Record, Pojo, Long>

  override fun findByIds(ids: Iterable<Id<Model>>): Set<Model> {
    val (withId, withoutId) = ids.partition { it.id != null }
    val (withUid, _) = withoutId.partition { it.uid != null }
    //todo adavliatov: add search by refs

    return findByCheckedIds(withId.checkedIds()) union findByCheckedUids(withUid.checkedUids())
  }
}

@Suppress("unused")
interface WithJooqFindByCheckedIds<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Model, Record, Pojo>,
  WithIdField<Record>,
  WithModelToPojo<Model, Pojo> {
  override val dao: DAO<Record, Pojo, Long>

  fun findByCheckedIds(ids: Iterable<Long>): Set<Model> = dao
    .fetch(idField.value, *ids.toSet().toTypedArray())
    .mapToSet { it.toEntity() }
}

@Suppress("unused")
interface WithJooqFindByCheckedUids<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Model, Record, Pojo>,
  WithUidField<Record>,
  WithModelToPojo<Model, Pojo> {
  override val dao: DAO<Record, Pojo, Long>

  fun findByCheckedUids(ids: Iterable<UUID>): Set<Model> = dao
    .fetch(uidField.value, *ids.toSet().toTypedArray())
    .mapToSet { it.toEntity() }
}