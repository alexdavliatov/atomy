package ru.adavliatov.atomy.toolkit.jooq.serialize.plugin.jackson

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.Converter
import ru.adavliatov.common.type.json.impl.gson.*
import ru.adavliatov.common.type.json.impl.gson.JacksonJson.Companion.toJson

class JsonConverter(val mapper: ObjectMapper = ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)) :
  Converter<Any, JacksonJson> {
  override fun from(t: Any?): JacksonJson =
    (t?.let { mapper.createObjectNode() } ?: mapper.readTree(t as String)).toJson()

  override fun to(u: JacksonJson?): String? = u?.run { mapper.writeValueAsString(node) }

  override fun fromType() = Any::class.java

  @Suppress("UNCHECKED_CAST")
  override fun toType(): Class<JacksonJson>? = JacksonJson::class.java
}
