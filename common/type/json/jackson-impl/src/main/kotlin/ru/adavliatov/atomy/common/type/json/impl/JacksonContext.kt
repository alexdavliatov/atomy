package ru.adavliatov.atomy.common.type.json.impl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import ru.adavliatov.atomy.common.type.json.Json
import ru.adavliatov.atomy.common.type.json.JsonContext
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson.Companion.toJson

data class JacksonContext(val mapper: ObjectMapper) : JsonContext {
  override fun toJson(obj: Any): JacksonJson = mapper
    .valueToTree<JsonNode>(obj)
    .toJson()

  override fun <T> fromJson(
    json: Json<*>,
    klass: Class<T>
  ) = mapper.treeToValue(
    (json as JacksonJson).node,
    klass
  )
}