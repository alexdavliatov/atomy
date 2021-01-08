package ru.adavliatov.atomy.common.type.json.impl

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import ru.adavliatov.atomy.common.type.json.Json
import ru.adavliatov.atomy.common.type.json.JsonContext
import ru.adavliatov.atomy.common.type.json.TypeRef
import ru.adavliatov.atomy.common.type.json.impl.GsonJson.Companion.toJson

data class GsonContext(val gson: Gson) :
  JsonContext {
  override fun toJson(obj: Any): GsonJson = toJson(gson.toJson(this))

  override fun <T> fromJson(
    json: Json<*>,
    klass: Class<T>
  ): T = gson.fromJson(
    (json as GsonJson).node,
    klass
  )

  override fun asString(json: Json<*>): String = (json as GsonJson).let { gson.toJson(it.node) }

  override fun <T> fromString(jsonAsSting: String, klass: Class<T>): T =
    gson.fromJson(jsonAsSting, klass)

  override fun fromString(jsonAsSting: String): GsonJson =
    gson.fromJson(jsonAsSting, JsonElement::class.java).toJson()

  override fun <T> fromString(jsonAsSting: String, typeRef: TypeRef<T>): T =
    gson.fromJson(jsonAsSting, (typeRef as GsonTypeRef<T>).typeToken.type) as T
}

class GsonTypeRef<T>(val typeToken: TypeToken<T>) : TypeRef<T>