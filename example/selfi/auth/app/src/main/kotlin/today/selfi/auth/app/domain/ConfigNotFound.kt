package today.selfi.auth.app.domain

import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors.NotFoundError
import today.selfi.auth.app.config.Environment

class ConfigNotFoundError(clazz: Class<*>, env: Environment) :
  NotFoundError(message = "${clazz.simpleName} not found for $env")
