package ru.adavliatov.atomy.common.service.domain.error

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.domain.error.HttpWrapperErrors.NotFoundError

object RepoErrors {
  class NotFoundRepoError(message: String) : NotFoundError(message = message) {
    constructor(type: Class<*>, id: Any?) : this("[${type.simpleName}] with id [$id] not found")
    constructor(id: Id<*>) : this(id.model::class.java, id)
  }
}
