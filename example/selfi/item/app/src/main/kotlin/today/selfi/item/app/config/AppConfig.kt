package today.selfi.item.app.config

import today.selfi.item.app.config.AppConfigDSL.appConfig
import today.selfi.item.app.config.Environment.DEV

inline class Port(val port: Int)
data class AppConfig(val port: Port)

object AppConfigs : Configs<AppConfig>(
  mapOf(DEV to appConfig {})
    .withDefault {
      appConfig {}
    }
)

object AppConfigDSL {
  fun appConfig(block: AppConfigBuilder.() -> Unit = {}) = AppConfigBuilder().apply(block).build()

  @DslMarker
  annotation class AppConfigDSL

  @AppConfigDSL
  class AppConfigBuilder {
    var port: Port = Port(8990)

    fun build() = AppConfig(port)
  }
}
