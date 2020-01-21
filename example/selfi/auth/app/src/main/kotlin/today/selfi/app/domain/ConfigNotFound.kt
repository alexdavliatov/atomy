package today.selfi.app.domain

import ru.adavliatov.atome.common.type.error.HttpWrapperErrors.NotFoundError
import today.selfi.app.config.Environment

class ConfigNotFoundError(clazz: Class<*>, env: Environment) :
  NotFoundError(message = "${clazz.simpleName} not found for $env")
