package ru.adavliatov.atomy.toolkit.jooq.service.behaviour

import org.jooq.DAO
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.service.repo.WithChunked
import ru.adavliatov.atomy.common.service.repo.WithChunkedAndHolder
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqDao
import ru.adavliatov.atomy.toolkit.jooq.service.WithJooqHolder
import ru.adavliatov.atomy.toolkit.jooq.service.WithModelToPojo
import ru.adavliatov.atomy.toolkit.jooq.service.WithOwnerField

@Suppress("unused")
interface WithJooqFindChunked<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithJooqDao<Model, Record, Pojo>,
  WithChunked<Model>,
  WithModelToPojo<Model, Pojo> {
  override val dao: DAO<Record, Pojo, Long>

  override fun findChunked(chunk: Chunk): ChunkedData<Model> = dsl
    .selectFrom(table)
    .limit(chunk.offset, chunk.limit)
    .fetch()
    .into(pojoClass)
    .map { it.toEntity() }
    .let { ChunkedData(chunk = chunk, items = it) }

}

@Suppress("unused")
interface WithJooqFindChunkedAndHolder<
    Holder : WithHolder,
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    HolderRecord : TableRecord<HolderRecord>,
    Pojo> : WithJooqDao<Model, Record, Pojo>,
  WithChunkedAndHolder<Holder, Model>,
  WithModelToPojo<Model, Pojo>,
  WithJooqHolder<Holder, HolderRecord>,
  WithOwnerField<Record> {
  override val dao: DAO<Record, Pojo, Long>

  override fun findChunked(holder: Holder, chunk: Chunk): ChunkedData<Model> = table
    .selectFromJoinHolder(ownerField.value)
    .where(holder.toCondition())
    .limit(chunk.offset, chunk.limit)
    .fetch()
    .let { records ->
      val items = records.into(table).into(pojoClass).map { it.toModel() }

      @Suppress("UNUSED_VARIABLE")
      val holderUid = records
        .into(holderTable)
        .into(holderUidField.value)
        .firstOrNull()
        ?.value1()

      items
    }
    .let { ChunkedData(chunk = chunk, items = it) }
}

