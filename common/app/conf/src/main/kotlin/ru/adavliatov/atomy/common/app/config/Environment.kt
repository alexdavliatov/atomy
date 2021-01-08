package ru.adavliatov.atomy.common.app.config

enum class Environment {
  DEV,
  TEST,
  TEST_CI,
  PROD,
  /**
   * General use only, not to be passed via system env.
   */
  DEFAULT,
}
