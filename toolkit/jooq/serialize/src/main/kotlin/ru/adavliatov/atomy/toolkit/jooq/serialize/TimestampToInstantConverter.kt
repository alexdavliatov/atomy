package ru.adavliatov.atomy.toolkit.jooq.serialize

import org.jooq.impl.AbstractConverter
import java.sql.Timestamp
import java.time.Instant

class TimestampToInstantConverter : AbstractConverter<Timestamp, Instant>(Timestamp::class.java, Instant::class.java) {
  override fun from(databaseObject: Timestamp?) = databaseObject?.toInstant()

  override fun to(userObject: Instant?) = userObject?.run { Timestamp.from(this) }
}
