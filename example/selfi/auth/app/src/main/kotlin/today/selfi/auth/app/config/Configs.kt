package today.selfi.auth.app.config

import today.selfi.auth.app.domain.ConfigNotFoundError

open class Configs<Config>(private val items: Map<Environment, Config>) {
  fun config(env: Environment) = items.getValue(env) ?: throw ConfigNotFoundError(javaClass, env)
}
