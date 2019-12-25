package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.States.DELETED
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.domain.ext.ModelExtensions.ids

interface EntityRepo<Entity : WithEntity<Entity>> : ReadOnlyEntityRepo<Entity>, WriteOnlyEntityRepo<Entity>

@Suppress("unused")
interface ModelRepo<Model : WithModel<Model>> : EntityRepo<Model> {
  override fun remove(model: Model) {
    @Suppress("UNCHECKED_CAST")
    modify(model.withState(DELETED) as Model)
  }
}

@Suppress("unused")
interface ReadOnlyEntityRepo<Entity : WithEntity<Entity>> : WithFindByIds<Entity> {
  fun findAll(): Iterable<Entity>

  fun count(): Long = findAll().count().toLong()

  fun existsById(id: Id<Entity>) = findById(id) != null
  fun exists(model: Entity): Boolean = existsById(model.id)
}

@Suppress("unused")
interface WriteOnlyEntityRepo<Entity : WithEntity<Entity>> {
  fun create(model: Entity) = create(setOf(model))
  fun create(vararg models: Entity) = create(models.toList())
  fun create(models: Iterable<Entity>)

  fun modify(model: Entity): Entity
  fun modify(vararg models: Entity) = modify(models.toList())
  fun modify(models: Iterable<Entity>)

  fun removeByIds(vararg ids: Id<Entity>) = removeByIds(ids.toList())
  fun removeByIds(ids: Iterable<Id<Entity>>)

  fun remove(model: Entity) = removeByIds(model.id)
  fun remove(vararg models: Entity) = removeByIds(models.toList().ids())
  fun remove(models: Iterable<Entity>) = removeByIds(models.ids())
}
