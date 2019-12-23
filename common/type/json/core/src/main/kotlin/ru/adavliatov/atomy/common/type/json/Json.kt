package ru.adavliatov.atomy.common.type.json

interface Json {
  fun asMap(): Map<String, Json>
  fun asArray(): List<Json>

  fun json(field: String): Json
  fun <T> value(field: String, clazz: Class<T>): T
}