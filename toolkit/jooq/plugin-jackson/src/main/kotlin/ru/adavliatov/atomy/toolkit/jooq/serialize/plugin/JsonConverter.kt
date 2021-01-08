package ru.adavliatov.atomy.toolkit.jooq.serialize.plugin

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.Converter
import org.jooq.JSONB
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson.Companion.toJson

class JsonConverter(val mapper: ObjectMapper = ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)) :
  Converter<JSONB, JacksonJson> {
  override fun from(t: JSONB?): JacksonJson =
    (t?.let { mapper.createObjectNode() as JsonNode } ?: mapper.readTree(t?.data())).toJson()

  override fun to(u: JacksonJson?): JSONB? = u?.run { JSONB.valueOf(mapper.writeValueAsString(node)) }

  override fun fromType() = JSONB::class.java

  @Suppress("UNCHECKED_CAST")
  override fun toType(): Class<JacksonJson>? = JacksonJson::class.java
}
