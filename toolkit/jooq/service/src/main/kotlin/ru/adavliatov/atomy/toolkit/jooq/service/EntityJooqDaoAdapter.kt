package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.DAO
import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.domain.ext.IdExtensions.checkedIds
import ru.adavliatov.atomy.common.service.repo.*
import javax.sql.DataSource

@Suppress("unused")
abstract class EntityJooqDaoAdapter<
    Model : WithEntity<Model>,
    Record : TableRecord<Record>,
    Pojo>(val ds: DataSource) : WithEntityJooqDaoAdapter<Model, Record, Pojo> {

  override val dsl: DSLContext
    get() = this.dao.configuration().dsl()
  override val table: Table<Record>
    get() = this.dao.table
}

interface WithEntityJooqDaoAdapter<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>,
    Pojo> : EntityRepo<Entity>, WithFindByIds<Entity>, WithEntityToPojo<Entity, Pojo> {

  val dao: DAO<Record, Pojo, Long>
  val dsl: DSLContext
    get() = dao.configuration().dsl()
  val table: Table<Record>
    get() = dao.table

  override fun create(models: Iterable<Entity>) = dao.insert(models.map { it.toPojo() })

  override fun modify(model: Entity): Entity = model.apply { dao.update(toPojo()) }
  override fun modify(models: Iterable<Entity>) = dao.update(models.map { it.toPojo() })
  override fun removeByIds(ids: Iterable<Id<Entity>>): Unit = dao.deleteById(ids.checkedIds().toSet())

  override fun findAll(): Iterable<Entity> = dao.findAll().map { it.toEntity() }

  override fun count(): Long = dao.count()
}

@Suppress("unused")
interface WithModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> :
  WithEntityJooqDaoAdapter<Model, Record, Pojo>,
  ModelRepo<Model> {
  @Suppress("UNCHECKED_CAST")
  override fun remove(model: Model) {
    modify(model.delete() as Model)
  }

  @Suppress("UNCHECKED_CAST")
  override fun remove(models: Iterable<Model>) {
    modify(models.map { it.delete() as Model })
  }
}

