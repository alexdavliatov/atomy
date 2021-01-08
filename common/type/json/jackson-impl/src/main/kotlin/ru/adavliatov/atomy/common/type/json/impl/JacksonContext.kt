package ru.adavliatov.atomy.common.type.json.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import ru.adavliatov.atomy.common.type.json.Json
import ru.adavliatov.atomy.common.type.json.JsonContext
import ru.adavliatov.atomy.common.type.json.TypeRef
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

  override fun asString(json: Json<*>): String = mapper.writeValueAsString((json as JacksonJson).node)

  @Suppress("UNCHECKED_CAST")
  override fun <T> fromString(jsonAsSting: String, klass: Class<T>): T =
    mapper.readValue(jsonAsSting, klass)

  @Suppress("UNCHECKED_CAST")
  override fun fromString(jsonAsSting: String): JacksonJson = mapper.readTree(jsonAsSting).toJson()

  @Suppress("UNCHECKED_CAST")
  override fun <T> fromString(jsonAsSting: String, typeRef: TypeRef<T>): T {
    typeRef as JacksonTypeRef<*>
    return mapper.readValue(jsonAsSting, typeRef.typeReference) as T
  }
}

class JacksonTypeRef<T>(val typeReference: TypeReference<T>) : TypeRef<T>