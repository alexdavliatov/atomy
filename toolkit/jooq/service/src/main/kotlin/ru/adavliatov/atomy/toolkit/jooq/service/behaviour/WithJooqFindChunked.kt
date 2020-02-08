package ru.adavliatov.atomy.toolkit.jooq.service.behaviour

import org.jooq.DAO
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.service.repo.WithFindChunked
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqDao
import ru.adavliatov.atomy.toolkit.jooq.service.WithModelToPojo

@Suppress("unused")
interface WithJooqFindChunked<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Model, Record, Pojo>,
  WithFindChunked<Model>,
  WithModelToPojo<Model, Pojo> {
  override val dao: DAO<Record, Pojo, Long>

  override fun findPaginated(chunk: Chunk): ChunkedData<Model> = dsl
    .selectFrom(table)
    .limit(chunk.limit, chunk.offset)
    .fetch()
    .into(pojoClass)
    .map { it.toEntity() }
    .let { ChunkedData(chunk = chunk, items = it) }

}

