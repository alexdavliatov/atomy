package ru.adavliatov.common.type.json.impl.gson

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import ru.adavliatov.atomy.common.ext.TypeExtensions.takeAs
import ru.adavliatov.atomy.common.type.json.*

@Suppress("MemberVisibilityCanBePrivate")
//todo (adavliatov): checks
class JacksonJson(val node: JsonNode) : Json<JacksonContext> {
  override fun isNull(): Boolean = node.isNull
  override fun isNode(): Boolean = node.isObject
  override fun isArray(): Boolean = node.isArray
  override fun isPrimitive(): Boolean = !node.isNull && node.isValueNode

  //todo (adavliatov): add required checks
  override fun asMap() = { _: JacksonContext ->
    node.fieldNames()
      .asSequence()
      .map { it to node.get(it).toJson() }
      .toMap()
  }

  override fun asArray() = { _: JacksonContext ->
    node.takeAs<ArrayNode> { IllegalArgumentException("Non array value is passed") }
      .elements()
      .asSequence()
      .map { JacksonJson(node) }
      .toList()
  }

  override fun json(field: String) = { _: JacksonContext ->
    node.get(field).toJson()
  }

  override fun <T> value(field: String, klass: Class<T>) = { context: JacksonContext ->
    context.mapper.convertValue(node.get(field), klass)
  }

  override fun <T> to(klass: Class<T>) = { context: JacksonContext ->
    context.mapper.treeToValue(node, klass)
  }

  companion object {
    fun JsonNode.toJson() = JacksonJson(this)
  }
}

data class JacksonContext(val mapper: ObjectMapper) : JsonContext