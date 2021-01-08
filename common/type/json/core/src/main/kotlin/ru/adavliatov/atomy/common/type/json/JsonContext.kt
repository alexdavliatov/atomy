package ru.adavliatov.atomy.common.type.json

interface JsonContext {
  fun toJson(obj: Any): Json<*>
  fun <T> fromJson(json: Json<*>, klass: Class<T>): T
  fun <T> fromString(jsonAsSting: String, typeRef: TypeRef<T>): T

  fun asString(json: Json<*>): String
  fun asString(obj: Any): String = asString(toJson(obj))

  fun <T> fromString(jsonAsSting: String, klass: Class<T>): T
  fun fromString(jsonAsSting: String): Json<*>
}
