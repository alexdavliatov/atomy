package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.domain.ext.ModelExtensions.ids
import ru.adavliatov.atomy.common.service.repo.ModelRepo
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFindByIds
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFindChunked
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqRemoveModelByIds
import javax.sql.DataSource


@Suppress("unused")
abstract class ModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo>(val ds: DataSource) :
  WithModelJooqDaoAdapter<Model, Record, Pojo> {

  override val dsl: DSLContext
    get() = dao.configuration().dsl()
  override val table: Table<Record>
    get() = dao.table
}

interface WithModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : ModelRepo<Model>,
  WithJooqFindByIds<Model, Record, Pojo>,
  WithJooqFindChunked<Model, Record, Pojo>,
  WithJooqRemoveModelByIds<Model, Record>,
  WithModelToPojo<Model, Pojo>,
  WithDSL {

  override fun create(items: Iterable<Model>) = dao.insert(items.map { it.toPojo() })
    .run { items.ids() }

  override fun modify(item: Model): Model = item.apply { dao.update(toPojo()) }
  override fun modify(items: Iterable<Model>) = dao.update(items.map { it.toPojo() })

  override fun removeByIds(ids: Iterable<Id<Model>>) {
    super<WithJooqRemoveModelByIds>.removeByIds(ids)
  }
}
