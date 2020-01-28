package today.selfi.auth.app.config

import today.selfi.auth.app.config.AppConfigDSL.appConfig
import today.selfi.auth.app.config.Environment.DEV

inline class Port(val port: Int)
data class AppConfig(val port: Port, val securePort: Port)

object AppConfigs : Configs<AppConfig>(
  mapOf(DEV to appConfig { })
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
    var port: Port = Port(8080)
    var securePort: Port = Port(8443)

    fun build() = AppConfig(port, securePort)
  }
}
