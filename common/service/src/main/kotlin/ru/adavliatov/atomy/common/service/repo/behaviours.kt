package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.service.domain.error.RepoErrors.NotFoundRepoError
import ru.adavliatov.atomy.common.service.repo.EntityCollectionExtensions.ids
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.common.type.ref.Refs
import ru.adavliatov.atomy.common.type.ref.WithRef

@Suppress("unused")
interface WithFetchOrCreate<One> {
  fun fetchOrCreate(item: One): One

  /**
   * The order matters.
   */
  fun fetchOrCreate(items: List<One>): List<One> = items.map { fetchOrCreate(it) }
}

@Suppress("unused")
interface WithFetchOrCreateAndHolder<Holder: WithHolder, One> {
  fun fetchOrCreate(holder: Holder, item: One): One

  /**
   * The order matters.
   */
  fun fetchOrCreate(holder: Holder, items: List<One>): List<One> = items.map { fetchOrCreate(holder, it) }
}

@Suppress("unused")
interface WithFindByRefs<Model : WithRef> {
  fun findByRefs(refs: Refs): Set<Model>
}

interface WithFindByIds<Id, Entity> {
  fun findById(id: Id): Entity? = findByIds(setOf(id)).firstOrNull()
  fun findByIdChecked(id: Id) = findById(id) ?: throw NotFoundRepoError("Entity with id=[$id] not found")
  fun findByIds(ids: Iterable<Id>): Set<Entity>
}

interface WithFindByIdsAndHolder<Holder: WithHolder, Id, Entity> {
  fun findById(holder: Holder, id: Id): Entity? = findByIds(holder, setOf(id)).firstOrNull()
  fun findByIdChecked(holder: Holder, id: Id) = findById(holder, id) ?: throw NotFoundRepoError("Entity with id=[$id] not found")
  fun findByIds(holder: Holder, ids: Iterable<Id>): Set<Entity>
}

interface WithChunked<Entity> {
  fun findChunked(chunk: Chunk): ChunkedData<Entity>
}

interface WithChunkedAndHolder<Holder: WithHolder, Entity> {
  fun findChunked(holder: Holder, chunk: Chunk): ChunkedData<Entity>
}

@Suppress("unused")
interface WithCreate<Id, One> {
  fun create(item: One) = create(setOf(item))
  fun create(vararg items: One) = create(items.toList())
  fun create(items: Iterable<One>): Iterable<Id>
}

@Suppress("unused")
interface WithCreateAndHolder<Holder: WithHolder, Id, One> {
  fun create(holder: Holder, item: One): Id? = create(holder, setOf(item)).firstOrNull()
  fun create(holder: Holder, vararg items: One) = create(holder, items.toList())
  fun create(holder: Holder, items: Iterable<One>): Iterable<Id> = items
    .asSequence()
    .mapNotNull { create(holder, it) as Any? }
    .map {
      @Suppress("UNCHECKED_CAST")
      it as Id
    }
    .toList()
}

@Suppress("unused")
interface WithModify<One> {
  //todo (adavliatov): reimplement if trash
  fun modify(item: One): One {
    modify(listOf(item))
    return item
  }

  fun modify(vararg items: One) = modify(items.toList())
  fun modify(items: Iterable<One>) = items.forEach { modify(it) }
}

@Suppress("unused")
interface WithModifyAndHolder<Holder: WithHolder, One> {
  //todo (adavliatov): reimplement if trash
  fun modify(holder: Holder, item: One): One {
    modify(holder, listOf(item))
    return item
  }

  fun modify(holder: Holder, vararg items: One) = modify(holder, items.toList())
  fun modify(holder: Holder, items: Iterable<One>) = items.forEach { modify(holder, it) }
}

interface WithRemoveByIds<Id, One> : WithRemoveCommon<One>, WithEntityToId<Id, One> {
  fun removeById(id: Id) = removeByIds(setOf(id))
  fun removeByIds(ids: Iterable<Id>)
  fun removeByIds(vararg ids: Id) = removeByIds(ids.toList())

  override fun remove(item: One) {
    item.id()?.let {
      removeById(it)
    }
  }

  override fun remove(vararg items: One) = removeByIds(items.toList().ids { it.id() })
  override fun remove(items: Iterable<One>) = removeByIds(items.ids { it.id() })
}

interface WithRemoveByIdsAndHolder<Holder: WithHolder, Id, One> : WithRemoveCommonAndHolder<Holder, One>, WithEntityToId<Id, One> {
  fun removeById(holder: Holder, id: Id) = removeByIds(holder, setOf(id))
  fun removeByIds(holder: Holder, ids: Iterable<Id>)
  fun removeByIds(holder: Holder, vararg ids: Id) = removeByIds(holder, ids.toList())

  override fun remove(holder: Holder, item: One) {
    item.id()?.let {
      removeById(holder, it)
    }
  }

  override fun remove(holder: Holder, vararg items: One) = removeByIds(holder, items.toList().ids { it.id() })
  override fun remove(holder: Holder, items: Iterable<One>) = removeByIds(holder, items.ids { it.id() })
}

interface WithRemoveCommon<One> {
  fun remove(item: One)
  fun remove(vararg items: One)
  fun remove(items: Iterable<One>)
}

interface WithRemoveCommonAndHolder<Holder: WithHolder, One> {
  fun remove(holder: Holder, item: One)
  fun remove(holder: Holder, vararg items: One)
  fun remove(holder: Holder, items: Iterable<One>)
}

