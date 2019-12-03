package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.States.DELETED
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.domain.ext.ModelExtensions.ids
import ru.adavliatov.atomy.common.service.domain.error.RepoErrors.NotFoundRepoError

interface EntityRepo<Entity : WithEntity<Entity>> : ReadOnlyEntityRepo<Entity>, WriteOnlyEntityRepo<Entity>

@Suppress("unused")
interface ModelRepo<Model : WithModel<Model>> : EntityRepo<Model> {
  override fun delete(model: Model) {
    @Suppress("UNCHECKED_CAST")
    update(model.withState(DELETED) as Model)
  }
}

@Suppress("unused")
interface ReadOnlyEntityRepo<Entity : WithEntity<Entity>> {
  fun findById(id: Id<Entity>): Entity?
  fun findByIdChecked(id: Id<Entity>) = findById(id) ?: throw NotFoundRepoError(id)
  fun findAll(): Iterable<Entity>

  fun count(): Int = findAll().count()

  fun existsById(id: Id<Entity>) = findById(id) != null
  fun exists(model: Entity): Boolean = existsById(model.id)
}

@Suppress("unused")
interface WriteOnlyEntityRepo<Entity : WithEntity<Entity>> {
  fun insert(model: Entity)
  fun insert(vararg models: Entity)
  fun insert(models: Collection<Entity>)

  fun update(model: Entity): Entity
  fun update(vararg models: Entity)
  fun update(models: Collection<Entity>)

  fun deleteByIds(vararg ids: Id<Entity>)
  fun deleteByIds(ids: Collection<Id<Entity>>)

  fun delete(model: Entity) = deleteByIds(model.id)
  fun delete(vararg models: Entity) = deleteByIds(models.toList().ids())
  fun delete(models: Collection<Entity>) = deleteByIds(models.ids())
}
