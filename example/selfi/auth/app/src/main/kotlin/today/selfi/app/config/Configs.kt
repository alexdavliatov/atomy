package today.selfi.app.config

import today.selfi.app.domain.ConfigNotFoundError

open class Configs<Config>(private val items: Map<Environment, Config>) {
  fun config(env: Environment) = items.getValue(env) ?: throw ConfigNotFoundError(javaClass, env)
}
