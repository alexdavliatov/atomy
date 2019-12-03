package ru.adavliatov.atomy.common.service.repo

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.ext.CollectionExtensions.mapToSet

@Suppress("unused")
interface WithInsertOrIgnore<One> {
  fun insertOrIgnore(model: One): One
  fun insertOrIgnore(models: Iterable<One>): Set<One> = models
    .mapToSet { insertOrIgnore(it) }
}

@Suppress("unused")
interface WithFindByIds<Entity : WithEntity<Entity>> {
  fun findByIds(ids: Set<Id<Entity>>): Set<Entity>
}

@Suppress("unused")
interface WithFindByExternalIds<Entity : WithRef> {
  fun findByExternalIds(refs: Refs): Set<Entity>
}
