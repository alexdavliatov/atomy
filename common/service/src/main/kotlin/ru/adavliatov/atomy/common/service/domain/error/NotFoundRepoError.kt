package ru.adavliatov.atomy.common.service.domain.error

import ru.adavliatov.atome.common.type.error.HttpWrapperErrors.NotFoundError
import ru.adavliatov.atomy.common.domain.Id

object RepoErrors {
  class NotFoundRepoError(message: String) : NotFoundError(message = message) {
    constructor(type: Class<*>, id: Any?) : this("[${type.simpleName}] with id [$id] not found")
    constructor(id: Id<*>) : this(id.model::class.java, id)
  }
}
