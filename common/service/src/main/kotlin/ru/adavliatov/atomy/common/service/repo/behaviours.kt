package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
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
interface WithFindByIds<Model : WithModel<Model>> {
  fun findById(id: Id<Model>): Model? = findByIds(setOf(id)).firstOrNull()
  fun findByIdChecked(id: Id<Model>) = findById(id) ?: throw NotFoundRepoError(id)
  fun findByIds(ids: Iterable<Id<Model>>): Set<Model>
}

@Suppress("unused")
interface WithFindChunked<Model : WithModel<Model>> {
  fun findPaginated(chunk: Chunk): ChunkedData<Model>
}

@Suppress("unused")
interface WithRemoveByIds<Model : WithModel<Model>> {
  fun removeById(id: Id<Model>) = removeByIds(setOf(id))
  fun removeByIds(ids: Iterable<Id<Model>>)
}

@Suppress("unused")
interface WithFindByRefs<Model : WithRef> {
  fun findByRefs(refs: Refs): Set<Model>
}
