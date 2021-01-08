package ru.adavliatov.atomy.common.app.config

import ru.adavliatov.atomy.common.app.domain.error.ConfigNotFoundError

open class Configs<Config>(private val items: Map<Environment, Config>) {
  constructor(vararg items: Pair<Environment, Config>): this(items.toMap())

  fun config(env: Environment) = items[env] ?: throw ConfigNotFoundError(
    javaClass,
    env
  )
  fun configOrDefault(env: Environment) = items[env] ?: items[Environment.DEFAULT] ?: throw ConfigNotFoundError(
    javaClass,
    env
  )
}
