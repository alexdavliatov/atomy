package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.service.repo.EntityRepo
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFindChunked
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFindByIds
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqRemoveEntityByIds
import javax.sql.DataSource

@Suppress("unused")
abstract class EntityJooqDaoAdapter<
    Model : WithEntity<Model>,
    Record : TableRecord<Record>,
    Pojo>(val ds: DataSource) : WithEntityJooqDaoAdapter<Model, Record, Pojo> {

  override val dsl: DSLContext
    get() = dao.configuration().dsl()
  override val table: Table<Record>
    get() = dao.table
}

interface WithEntityJooqDaoAdapter<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>,
    Pojo> : EntityRepo<Entity>,
  WithJooqFindByIds<Entity, Record, Pojo>,
  WithJooqFindChunked<Entity, Record, Pojo>,
  WithJooqRemoveEntityByIds<Entity, Record>,
  WithEntityToPojo<Entity, Pojo>,
  WithDsl {
  override fun create(models: Iterable<Entity>) = dao.insert(models.map { it.toPojo() })

  override fun modify(model: Entity): Entity = model.apply { dao.update(toPojo()) }
  override fun modify(models: Iterable<Entity>) = dao.update(models.map { it.toPojo() })
  override fun findAll(): Collection<Entity> = dao.findAll().map { it.toEntity() }

  override fun count(): Long = dao.count()
}
