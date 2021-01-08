package ru.adavliatov.atomy.common.service.repo

interface CommonRepoAndHolder<Holder: WithHolder, Id, Entity> : ReadOnlyCommonRepoAndHolder<Holder, Id, Entity>, WriteOnlyCommonRepoAndHolder<Holder, Id, Entity>,
  WithEntityToId<Id, Entity>

@Suppress("unused")
interface ReadOnlyCommonRepoAndHolder<Holder: WithHolder, Id, Entity> :
  WithFindByIdsAndHolder<Holder, Id, Entity>,
  WithChunkedAndHolder<Holder, Entity>,
  WithEntityToId<Id, Entity> {
  fun existsById(holder: Holder, id: Id?) = id?.let { findById(holder, it) != null } ?: false
  fun exists(holder: Holder, model: Entity): Boolean = existsById(holder, model.id())
}

@Suppress("unused")
interface WriteOnlyCommonRepoAndHolder<Holder: WithHolder, Id, Entity> :
  WithFetchOrCreateAndHolder<Holder, Entity>,
  WithRemoveByIdsAndHolder<Holder, Id, Entity>,
  WithCreateAndHolder<Holder, Id, Entity>,
  WithModifyAndHolder<Holder, Entity>,
  WithEntityToId<Id, Entity>
