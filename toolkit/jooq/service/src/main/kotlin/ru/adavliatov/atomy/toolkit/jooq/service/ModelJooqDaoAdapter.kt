package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.WithEntity
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.service.repo.ModelRepo
import javax.sql.DataSource

@Suppress("unused")
abstract class ModelJooqDaoAdapter<
        Model : WithModel<Model>,
        Record : TableRecord<Record>,
        Pojo>(val ds: DataSource) : WithModelJooqDaoAdapter<Model, Record, Pojo> {

  override val dsl: DSLContext
    get() = this.dao.configuration().dsl()
  override val table: Table<Record>
    get() = this.dao.table
}

@Suppress("unused")
interface WithModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> : WithEntityJooqDaoAdapter<Model, Record, Pojo>, ModelRepo<Model> {
  @Suppress("UNCHECKED_CAST")
  override fun remove(model: Model) {
    modify(model.delete() as Model)
  }

  @Suppress("UNCHECKED_CAST")
  override fun remove(models: Iterable<Model>) {
    modify(models.map { it.delete() as Model })
  }
}
