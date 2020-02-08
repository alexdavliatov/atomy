package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.domain.ext.ModelExtensions.ids

interface ModelRepo<Model : WithModel<Model>> : ReadOnlyModelRepo<Model>, WriteOnlyModelRepo<Model>

@Suppress("unused")
interface ReadOnlyModelRepo<Model : WithModel<Model>> : WithFindByIds<Model>, WithFindChunked<Model> {
  fun findAll(): Collection<Model>

  fun count(): Long = findAll().count().toLong()

  fun existsById(id: Id<Model>) = findById(id) != null
  fun exists(model: Model): Boolean = existsById(model.id)
}

@Suppress("unused")
interface WriteOnlyModelRepo<Model : WithModel<Model>> : WithRemoveByIds<Model> {
  fun create(model: Model) = create(setOf(model))
  fun create(vararg models: Model) = create(models.toList())
  fun create(models: Iterable<Model>)

  fun modify(model: Model): Model
  fun modify(vararg models: Model) = modify(models.toList())
  fun modify(models: Iterable<Model>)

  fun removeByIds(vararg ids: Id<Model>) = removeByIds(ids.toList())

  fun remove(model: Model) = removeByIds(model.id)
  fun remove(vararg models: Model) = removeByIds(models.toList().ids())
  fun remove(models: Iterable<Model>) = removeByIds(models.ids())
}
