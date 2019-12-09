package ru.adavliatov.atomy.toolkit.jooq.ext

import org.jooq.Configuration
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import javax.sql.DataSource

@Suppress("unused")
object JooqExtensions {
  val Configuration.ds: DataSource
    get() = (connectionProvider() as DataSourceConnectionProvider).dataSource()

  fun DataSource.toJooqConfig(): Configuration = DefaultConfiguration()
    .set(this)
}
