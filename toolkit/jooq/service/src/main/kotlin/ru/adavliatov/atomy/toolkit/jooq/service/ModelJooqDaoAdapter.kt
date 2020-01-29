package ru.adavliatov.atomy.toolkit.jooq.service

import org.jooq.TableRecord
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.service.repo.ModelRepo
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqFindByIds
import ru.adavliatov.atomy.toolkit.jooq.service.behaviour.WithJooqRemoveModelByIds
import javax.sql.DataSource

@Suppress("unused")
abstract class ModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo>(val ds: DataSource) :
  WithModelJooqDaoAdapter<Model, Record, Pojo>

interface WithModelJooqDaoAdapter<
    Model : WithModel<Model>,
    Record : TableRecord<Record>,
    Pojo> :
  WithEntityJooqDaoAdapter<Model, Record, Pojo>,
  WithJooqFindByIds<Model, Record, Pojo>,
  WithJooqRemoveModelByIds<Model, Record>,
  ModelRepo<Model>,
  WithModelToPojo<Model, Pojo> {
  override fun removeByIds(ids: Iterable<Id<Model>>) {
    super<WithJooqRemoveModelByIds>.removeByIds(ids)
  }
}