package ru.adavliatov.atomy.common.service.repo

interface CommonRepo<Id, Entity> : ReadOnlyCommonRepo<Id, Entity>, WriteOnlyCommonRepo<Id, Entity>,
  WithEntityToId<Id, Entity>

interface WithEntityToId<Id, Entity> {
  fun Entity.id(): Id?
}

@Suppress("unused")
interface ReadOnlyCommonRepo<Id, Entity> :
  WithFindByIds<Id, Entity>,
  WithChunked<Entity>,
  WithEntityToId<Id, Entity> {
  fun existsById(id: Id?) = id?.let { findById(it) != null } ?: false
  fun exists(model: Entity): Boolean = existsById(model.id())
}

@Suppress("unused")
interface WriteOnlyCommonRepo<Id, Entity> :
  WithFetchOrCreate<Entity>,
  WithRemoveByIds<Id, Entity>,
  WithEntityToId<Id, Entity>,
  WithCreate<Id, Entity>,
  WithModify<Entity>

object EntityCollectionExtensions {
  @Suppress("UNCHECKED_CAST")
  fun <Id, Entity> Iterable<Entity>.ids(idExtractor: (Entity) -> Id?) = asSequence()
    .map { idExtractor(it) }
    .filterNot { it == null } as Iterable<Id>
}