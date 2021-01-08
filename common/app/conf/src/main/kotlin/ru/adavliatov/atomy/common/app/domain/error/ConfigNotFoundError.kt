package ru.adavliatov.atomy.common.app.domain.error

import ru.adavliatov.atomy.common.app.config.Environment
import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors.NotFoundError

class ConfigNotFoundError(clazz: Class<*>, env: Environment) :
  NotFoundError(message = "${clazz.simpleName} not found for $env")
