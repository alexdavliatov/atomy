package ru.adavliatov.atomy.common.type.json.impl.builder

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode

/**
 * Convenience [JsonNode] builder.
 */
object JsonNodeBuilders {
  fun node(k1: String?, v1: Boolean): ObjectNodeBuilder = node().with(k1, v1)
  fun node(k1: String?, v1: Int): ObjectNodeBuilder = node().with(k1, v1)
  fun node(k1: String?, v1: Float): ObjectNodeBuilder = node().with(k1, v1)
  fun node(k1: String?, v1: String?): ObjectNodeBuilder = node().with(k1, v1)
  fun node(
    k1: String?, v1: String?, k2: String?,
    v2: String?
  ): ObjectNodeBuilder = node(
    k1,
    v1
  ).with(k2, v2)

  fun node(
    k1: String?, v1: String?, k2: String?, v2: String?,
    k3: String?, v3: String?
  ): ObjectNodeBuilder = node(
    k1,
    v1,
    k2,
    v2
  ).with(k3, v3)

  fun node(k1: String?, builder: JsonNodeBuilder<*>): ObjectNodeBuilder = node().with(k1, builder)

  /**
   * Factory methods for an [ObjectNode] builder.
   */
  @JvmOverloads
  fun node(factory: JsonNodeFactory = JsonNodeFactory.instance): ObjectNodeBuilder =
    ObjectNodeBuilder(factory)

  /**
   * Factory methods for an [ArrayNode] builder.
   */
  fun array(): ArrayNodeBuilder =
    array(JsonNodeFactory.instance)

  fun array(vararg values: Boolean): ArrayNodeBuilder = array().with(*values)
  fun array(vararg values: Int): ArrayNodeBuilder = array().with(*values)
  fun array(vararg values: String?): ArrayNodeBuilder = array().with(*values)
  fun array(vararg builders: JsonNodeBuilder<*>?): ArrayNodeBuilder = array().with(*builders)
  fun array(factory: JsonNodeFactory): ArrayNodeBuilder =
    ArrayNodeBuilder(factory)

  interface JsonNodeBuilder<T : JsonNode?> {
    /**
     * Construct and return the [JsonNode] instance.
     */
    fun end(): T
  }

  abstract class AbstractNodeBuilder<T : JsonNode?>(
    /**
     * The source of values.
     */
    protected val factory: JsonNodeFactory? = null,
    /**
     * The value under construction.
     */
    protected val node: T? = null
  ) : JsonNodeBuilder<T> {

    /**
     * Returns a valid JSON string, so long as `POJONode`s not used.
     */
    override fun toString(): String {
      return node.toString()
    }
  }

  class ObjectNodeBuilder(factory: JsonNodeFactory) :
    AbstractNodeBuilder<ObjectNode?>(factory, factory.objectNode()) {
    fun withNull(field: String?): ObjectNodeBuilder = with(field, factory?.nullNode())
    fun with(value: Int): ObjectNodeBuilder = with(factory?.numberNode(value))
    fun with(value: Float): ObjectNodeBuilder = with(factory?.numberNode(value))
    fun with(value: Boolean): ObjectNodeBuilder = with(factory?.booleanNode(value))
    fun with(value: String?): ObjectNodeBuilder = with(factory?.textNode(value))
    fun with(value: JsonNode?): ObjectNodeBuilder = with("value", value)

    fun with(field: String?, value: Int): ObjectNodeBuilder = with(field, factory?.numberNode(value))
    fun with(field: String?, value: Float): ObjectNodeBuilder = with(field, factory?.numberNode(value))
    fun with(field: String?, value: Boolean): ObjectNodeBuilder = with(field, factory?.booleanNode(value))
    fun with(field: String?, value: String?): ObjectNodeBuilder = with(field, factory?.textNode(value))
    fun with(field: String?, value: JsonNode?): ObjectNodeBuilder {
      node?.set<JsonNode>(field, value)
      return this
    }

    fun with(field: String?, builder: JsonNodeBuilder<*>): ObjectNodeBuilder = with(field, builder.end())

    fun withPOJO(field: String?, pojo: Any?): ObjectNodeBuilder = with(field, factory?.pojoNode(pojo))

    override fun end(): ObjectNode? = node
  }

  class ArrayNodeBuilder(factory: JsonNodeFactory) :
    AbstractNodeBuilder<ArrayNode?>(factory, factory.arrayNode()) {
    fun with(value: Boolean): ArrayNodeBuilder {
      node?.add(value)
      return this
    }

    fun with(vararg values: Boolean): ArrayNodeBuilder {
      for (value in values) with(value)
      return this
    }

    fun with(value: Int): ArrayNodeBuilder {
      node?.add(value)
      return this
    }

    fun with(vararg values: Int): ArrayNodeBuilder {
      for (value in values) with(value)
      return this
    }

    fun with(value: Float): ArrayNodeBuilder {
      node?.add(value)
      return this
    }

    fun with(value: String?): ArrayNodeBuilder {
      node?.add(value)
      return this
    }

    fun with(vararg values: String?): ArrayNodeBuilder {
      for (value in values) with(value)
      return this
    }

    fun with(values: Iterable<String?>): ArrayNodeBuilder {
      for (value in values) with(value)
      return this
    }

    fun with(value: JsonNode?): ArrayNodeBuilder {
      node?.add(value)
      return this
    }

    fun with(vararg values: JsonNode?): ArrayNodeBuilder {
      for (value in values) with(value)
      return this
    }

    fun with(value: JsonNodeBuilder<*>): ArrayNodeBuilder {
      return with(value.end())
    }

    fun with(vararg builders: JsonNodeBuilder<*>?): ArrayNodeBuilder {
      for (builder in builders) with(builder)
      return this
    }

    override fun end(): ArrayNode? {
      return node
    }
  }
}