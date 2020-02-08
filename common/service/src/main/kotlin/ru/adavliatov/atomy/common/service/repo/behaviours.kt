package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.service.domain.error.RepoErrors.NotFoundRepoError
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.common.type.ref.Refs
import ru.adavliatov.atomy.common.type.ref.WithRef

@Suppress("unused")
interface WithFetchOrCreate<One> {
  fun fetchOrCreate(model: One): One
  fun fetchOrCreate(models: Iterable<One>): List<One> = models.map { fetchOrCreate(it) }
}

@Suppress("unused")
interface WithFindByIds<Entity : WithEntity<Entity>> {
  fun findById(id: Id<Entity>): Entity? = findByIds(setOf(id)).firstOrNull()
  fun findByIdChecked(id: Id<Entity>) = findById(id) ?: throw NotFoundRepoError(id)
  fun findByIds(ids: Iterable<Id<Entity>>): Set<Entity>
}

@Suppress("unused")
interface WithFindChunked<Entity : WithEntity<Entity>> {
  fun findPaginated(chunk: Chunk): ChunkedData<Entity>
}

@Suppress("unused")
interface WithRemoveByIds<Entity : WithEntity<Entity>> {
  fun removeById(id: Id<Entity>) = removeByIds(setOf(id))
  fun removeByIds(ids: Iterable<Id<Entity>>)
}

@Suppress("unused")
interface WithFindByExternalIds<Entity : WithRef> {
  fun findByExternalIds(refs: Refs): Set<Entity>
}
