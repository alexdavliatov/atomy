package ru.adavliatov.atomy.common.type.json.impl

import com.google.gson.JsonElement
import ru.adavliatov.atomy.common.type.json.Json

@Suppress("MemberVisibilityCanBePrivate")
//todo (adavliatov): checks
class GsonJson(val node: JsonElement) : Json<GsonContext> {
  override fun isNull(): Boolean = node.isJsonNull
  override fun isNode(): Boolean = node.isJsonObject
  override fun isArray(): Boolean = node.isJsonArray
  override fun isPrimitive(): Boolean = node.isJsonPrimitive

  //todo (adavliatov): add required checks
  override fun asMap() = { _: GsonContext ->
    node.asJsonObject
      .entrySet()
      .asSequence()
      .map { it.key to it.value.toJson() }
      .toMap()
  }

  override fun asArray() = { _: GsonContext ->
    node.asJsonArray
      .map { it.toJson() }
  }

  override fun json(field: String) = { _: GsonContext ->
    node.nodeAt(field).toJson()
  }

  override fun <T> value(field: String, klass: Class<T>) = { context: GsonContext ->
    context.gson.fromJson(node.nodeAt(field), klass)
  }

  //todo (adavliatov): deal with TypeToken
  override fun <T> to(klass: Class<T>) = { context: GsonContext ->
    context.gson.fromJson(node, klass)
  }

  private inline fun <reified T> JsonElement.to(context: GsonContext): T = context.gson.fromJson(this, T::class.java)

  companion object {
    fun JsonElement.nodeAt(field: String): JsonElement = asJsonObject.getAsJsonObject(field)
    fun JsonElement.toJson() = GsonJson(this)
  }
}

