package today.selfi.app.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.PrintWriter
import java.sql.Connection
import java.util.logging.*
import javax.sql.DataSource

class DataSourceWrapper(private val dbConfig: DbConfig) : DataSource {
  private val hikariDS = HikariDataSource(dbConfig.toHikariConfig())

  override fun setLogWriter(out: PrintWriter?) {
    hikariDS.logWriter
  }

  override fun getParentLogger(): Logger = hikariDS.parentLogger

  override fun setLoginTimeout(seconds: Int) {
    hikariDS.loginTimeout = seconds
  }

  override fun isWrapperFor(iface: Class<*>?) = hikariDS.isWrapperFor(iface)

  override fun getLogWriter(): PrintWriter = hikariDS.logWriter

  override fun <T : Any?> unwrap(iface: Class<T>?): T = hikariDS.unwrap(iface)

  override fun getConnection(): Connection = hikariDS.connection

  override fun getConnection(username: String?, password: String?): Connection =
    hikariDS.getConnection(username, password)

  override fun getLoginTimeout() = hikariDS.loginTimeout
}

fun DbConfig.toHikariConfig() = HikariConfig().apply {
  jdbcUrl = this@toHikariConfig.url
  username = this@toHikariConfig.username
  password = this@toHikariConfig.password
}
