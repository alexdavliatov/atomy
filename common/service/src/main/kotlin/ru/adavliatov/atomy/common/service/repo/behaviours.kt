package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet
import ru.adavliatov.atomy.common.service.domain.error.RepoErrors.NotFoundRepoError

@Suppress("unused")
interface WithFetchOrCreate<One> {
  fun fetchOrCreate(model: One): One
  fun fetchOrCreate(models: Iterable<One>): Set<One> = models.mapToSet { fetchOrCreate(it) }
}

@Suppress("unused")
interface WithFindByIds<Entity : WithEntity<Entity>> {
  fun findById(id: Id<Entity>): Entity? = findByIds(setOf(id)).firstOrNull()
  fun findByIdChecked(id: Id<Entity>) = findById(id) ?: throw NotFoundRepoError(id)
  fun findByIds(ids: Iterable<Id<Entity>>): Set<Entity>
}

@Suppress("unused")
interface WithFindByExternalIds<Entity : WithRef> {
  fun findByExternalIds(refs: Refs): Set<Entity>
}
