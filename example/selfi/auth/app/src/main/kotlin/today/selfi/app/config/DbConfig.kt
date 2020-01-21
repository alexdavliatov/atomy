package today.selfi.app.config

import today.selfi.app.config.DbConfigDSL.dbConfig
import today.selfi.app.config.Environment.DEV

data class DbConfig(
  val url: String,
  val username: String,
  val password: String
)

object DbConfigs : Configs<DbConfig>(
  mapOf(
    DEV to dbConfig {
      url = "jdbc:postgresql://dockerhost:9990/selfi_dev"
      username = "selfi_admin"
      password = "yIe7fq4h#k!KOCthDo5r@Jt"
    }
  )
)

object DbConfigDSL {
  fun dbConfig(block: DbConfigBuilder.() -> Unit = {}) = DbConfigBuilder().apply(block).build()

  @DslMarker
  annotation class DbConfigDSL

  @DbConfigDSL
  class DbConfigBuilder {
    var url: String = ""
    var username: String = ""
    var password: String = ""

    fun build() = DbConfig(url, username, password)
  }
}
