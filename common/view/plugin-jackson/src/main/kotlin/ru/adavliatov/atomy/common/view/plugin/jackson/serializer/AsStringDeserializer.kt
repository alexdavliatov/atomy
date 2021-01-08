package ru.adavliatov.atomy.common.view.plugin.jackson.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer

//todo (adavliatov): move to its own module
class AsStringDeserializer<T>(val klass: Class<T>, val fromString: (String) -> T) : StdScalarDeserializer<T>(klass) {
  override fun deserialize(parser: JsonParser, ctx: DeserializationContext): T? = parser.valueAsString?.let(fromString)
}
