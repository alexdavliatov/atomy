package ru.adavliatov.atomy.common.service.domain.error

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.NotFoundError

object RepoErrors {
  class NotFoundRepoError(type: Class<*>, id: Any?) :
    NotFoundError(message = "[${type.simpleName}] with id [$id] not found") {
    constructor(id: Id<*>) : this(id.model::class.java, id)
  }
}
