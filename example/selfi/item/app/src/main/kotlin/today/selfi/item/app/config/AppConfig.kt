package today.selfi.item.app.config

import today.selfi.item.app.config.AppConfigDSL.appConfig
import today.selfi.item.app.config.Environment.DEV

inline class Port(val port: Int)
data class AppConfig(val port: Port)

object AppConfigs : Configs<AppConfig>(
  mapOf(DEV to appConfig { port = Port(9900) })
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
    var port: Port =
      Port(85)

    fun build() = AppConfig(port)
  }
}
