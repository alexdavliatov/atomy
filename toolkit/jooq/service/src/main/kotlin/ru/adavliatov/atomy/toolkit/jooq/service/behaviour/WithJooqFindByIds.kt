package ru.adavliatov.atomy.toolkit.jooq.service.behaviour

import org.jooq.DAO
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedIds
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedUids
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import ru.adavliatov.atomy.common.service.repo.WithFindByIds
import ru.adavliatov.atomy.toolkit.jooq.service.WithEntityToPojo
import ru.adavliatov.atomy.toolkit.jooq.service.WithIdField
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqDao
import ru.adavliatov.atomy.toolkit.jooq.service.WithUidField
import java.util.UUID

@Suppress("unused")
interface WithJooqFindByIds<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Entity, Record, Pojo>,
  WithJooqFindByCheckedIds<Entity, Record, Pojo>,
  WithJooqFindByCheckedUids<Entity, Record, Pojo>,
  WithUidField<Record>,
  WithFindByIds<Entity> {
  override val dao: DAO<Record, Pojo, Long>

  override fun findByIds(ids: Iterable<Id<Entity>>): Set<Entity> {
    val (withId, withoutId) = ids.partition { it.id != null }
    val (withUid, _) = withoutId.partition { it.uid != null }
    //todo adavliatov: add search by refs

    return findByCheckedIds(withId.checkedIds()) union findByCheckedUids(withUid.checkedUids())
  }
}

@Suppress("unused")
interface WithJooqFindByCheckedIds<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Entity, Record, Pojo>,
  WithIdField<Record>,
  WithEntityToPojo<Entity, Pojo> {
  override val dao: DAO<Record, Pojo, Long>

  fun findByCheckedIds(ids: Iterable<Long>): Set<Entity> = dao
    .fetch(idField.value, *ids.toSet().toTypedArray())
    .mapToSet { it.toEntity() }
}

@Suppress("unused")
interface WithJooqFindByCheckedUids<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Entity, Record, Pojo>,
  WithUidField<Record>,
  WithEntityToPojo<Entity, Pojo> {
  override val dao: DAO<Record, Pojo, Long>

  fun findByCheckedUids(ids: Iterable<UUID>): Set<Entity> = dao
    .fetch(uidField.value, *ids.toSet().toTypedArray())
    .mapToSet { it.toEntity() }
}