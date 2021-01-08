package ru.adavliatov.atomy.common.app.config

data class DbConfig(
  val url: String,
  val username: String,
  val password: String
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