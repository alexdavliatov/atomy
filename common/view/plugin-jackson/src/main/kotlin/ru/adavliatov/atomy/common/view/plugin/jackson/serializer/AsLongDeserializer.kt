package ru.adavliatov.atomy.common.view.plugin.jackson.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer

//todo (adavliatov): move to its own module
class AsLongDeserializer<T>(val klass: Class<T>, val converter: (Long) -> T) : StdScalarDeserializer<T>(klass) {
  override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T? = p.valueAsLong.let(converter)
}
