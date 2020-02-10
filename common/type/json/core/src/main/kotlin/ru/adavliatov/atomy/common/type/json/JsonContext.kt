package ru.adavliatov.atomy.common.type.json

interface JsonContext {
  fun toJson(obj: Any): Json<*>
  fun <T> fromJson(json: Json<*>, klass: Class<T>): T
}
