package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.DSLContext
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.service.repo.*
import javax.sql.DataSource

@Suppress("unused")
abstract class ModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo>(val ds: DataSource) : WithModelJooqDaoAdapter<Model, Record, Pojo>,
  WithJooqFindByIds<Model, Record, Pojo>,
  WithIdField<Record> {

  override val dsl: DSLContext
    get() = dao.configuration().dsl()
}

@Suppress("unused")
interface WithModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithEntityJooqDaoAdapter<Model, Record, Pojo>, ModelRepo<Model>, WithModelToPojo<Model, Pojo> {
  @Suppress("UNCHECKED_CAST")
  override fun remove(model: Model) {
    modify(model.delete() as Model)
  }

  @Suppress("UNCHECKED_CAST")
  override fun remove(models: Iterable<Model>) {
    modify(models.map { it.delete() as Model })
  }
}
