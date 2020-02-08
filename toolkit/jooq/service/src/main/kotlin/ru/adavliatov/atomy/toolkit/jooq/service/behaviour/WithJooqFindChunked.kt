package ru.adavliatov.atomy.toolkit.jooq.service.behaviour

import org.jooq.DAO
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.service.repo.WithFindChunked
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.toolkit.jooq.service.WithEntityToPojo
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqDao

@Suppress("unused")
interface WithJooqFindChunked<
    Entity : WithEntity<Entity>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Entity, Record, Pojo>,
  WithFindChunked<Entity>,
  WithEntityToPojo<Entity, Pojo> {
  override val dao: DAO<Record, Pojo, Long>

  override fun findPaginated(chunk: Chunk): ChunkedData<Entity> = dsl
    .selectFrom(table)
    .limit(chunk.limit, chunk.offset)
    .fetch()
    .into(pojoClass)
    .map { it.toEntity() }
    .let { ChunkedData(chunk, it) }

}

