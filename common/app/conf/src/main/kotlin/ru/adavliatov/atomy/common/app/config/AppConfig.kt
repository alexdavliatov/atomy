package ru.adavliatov.atomy.common.app.config

@JvmInline
value class Host(val host: String)
@JvmInline
value class Port(val port: Int)
data class AppConfig(
  private val host: Host,
  private val port: Lazy<Port>
) {
  fun host() = host.host
  fun port() = port.value.port
}

object AppConfigDSL {
  fun appConfig(block: AppConfigBuilder.() -> Unit = {}) = AppConfigBuilder().apply(block).build()

  @DslMarker
  annotation class AppConfigDSL

  @AppConfigDSL
  class AppConfigBuilder {
    var host: Host = Host("localhost")
    var port: Lazy<Port> = lazy { Port(8443) }

    fun build() = AppConfig(host, port)
  }
}
