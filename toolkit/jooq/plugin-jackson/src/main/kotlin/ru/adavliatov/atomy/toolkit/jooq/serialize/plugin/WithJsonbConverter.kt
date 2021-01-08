package ru.adavliatov.atomy.toolkit.jooq.serialize.plugin

import org.jooq.JSONB
import ru.adavliatov.atomy.common.type.json.Json
import ru.adavliatov.atomy.common.type.json.impl.JacksonContext

interface WithJsonbConverter {
  val context: JacksonContext

  fun Json<*>.toJsonb() = JSONB.valueOf(context.asString(this))
  fun <T> JSONB.toJson(klass: Class<T>) = context.fromString(data(), klass)
  fun JSONB.toJson(): Json<*> = context.fromString(data())
}
