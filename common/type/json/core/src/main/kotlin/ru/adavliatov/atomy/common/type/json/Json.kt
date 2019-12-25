package ru.adavliatov.atomy.common.type.json

interface Json<Context : JsonContext> {
  fun isNull(): Boolean
  fun isNode(): Boolean
  fun isArray(): Boolean
  fun isPrimitive(): Boolean

  fun asMap(): Function1<Context, Map<String, Json<Context>>>
  fun asArray(): Function1<Context, List<Json<Context>>>

  fun json(field: String): Function1<Context, Json<Context>>
  fun <T> value(field: String, klass: Class<T>): Function1<Context, T>
  fun <T> to(klass: Class<T>): Function1<Context, T>
}