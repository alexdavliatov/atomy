package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.domain.ext.ModelExtensions.ids
import ru.adavliatov.atomy.common.service.repo.ModelRepoAndHolder
import ru.adavliatov.atomy.common.service.repo.WithHolder
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFindByIdsAndHolder
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFindChunkedAndHolder
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqRemoveModelByIdsAndHolder
import javax.sql.DataSource


@Suppress("unused")
abstract class ModelJooqDaoAdapterAndHolder<
    Holder : WithHolder,
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    HolderRecord : TableRecord<HolderRecord>,
    Pojo>(val ds: DataSource) :
  WithModelJooqDaoAdapterAndHolder<Holder, Model, Record, HolderRecord, Pojo> {

  override val dsl: DSLContext
    get() = dao.configuration().dsl()
  override val table: Table<Record>
    get() = dao.table
}

interface WithModelJooqDaoAdapterAndHolder<
    Holder : WithHolder,
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    HolderRecord : TableRecord<HolderRecord>,
    Pojo> : ModelRepoAndHolder<Holder, Model>,
  WithJooqFindByIdsAndHolder<Holder, Model, Record, HolderRecord, Pojo>,
  WithJooqFindChunkedAndHolder<Holder, Model, Record, HolderRecord, Pojo>,
  WithJooqRemoveModelByIdsAndHolder<Holder, Model, Record>,
  WithModelToPojo<Model, Pojo>,
  WithDSL {

  override fun create(holder: Holder, items: Iterable<Model>) = dao.insert(items.map { it.toPojo() })
    .run { items.ids() }

  override fun modify(holder: Holder, item: Model): Model = item.apply { dao.update(toPojo()) }
  override fun modify(holder: Holder, items: Iterable<Model>) = dao.update(items.map { it.toPojo() })

  override fun removeByIds(holder: Holder, ids: Iterable<Id<Model>>) {
    super<WithJooqRemoveModelByIdsAndHolder>.removeByIds(holder, ids)
  }
}
