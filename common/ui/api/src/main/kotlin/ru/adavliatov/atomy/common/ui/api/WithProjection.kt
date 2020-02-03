package ru.adavliatov.atomy.common.ui.api

interface WithResolvers<Field> {
  val resolvers: Map<Field, ProjectionResolver<*, *>>
}

interface WithProjection<Field> {
  val projection: Projection<Field>
}

data class Projection<Field>(
  val plusFields: Set<Field> = emptySet(),
  val minusFields: Set<Field> = emptySet()
) {

  val full: Boolean
    get() = plusFields.isNullOrEmpty() && minusFields.isNullOrEmpty()

  operator fun contains(field: Field) = full
      || field in plusFields
      || field !in minusFields

  fun <Value> resolve(field: Field, resolver: () -> Value): Value? = if (contains(field)) resolver() else null

  fun <Value> resolveOrDefault(field: Field, default: Value, resolver: () -> Value): Value =
    if (contains(field)) resolver() else default

  fun plusField(field: Field) = copy(plusFields = plusFields + field)
  fun minusField(field: Field) = copy(minusFields = minusFields + field)

  companion object {
    fun <Field> full() = Projection<Field>()
    fun <Field> minusFields(vararg fields: Field) = Projection(minusFields = fields.toSet())
  }
}

interface ProjectionResolver<Context, Value> {
  operator fun invoke(context: Context): Value
}
