package ru.adavliatov.atomy.common.ui.api.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer

//todo (adavliatov): move to its own module
class AsLongSerializer<T>(klass: Class<T>, val converter: (T) -> Long) : StdScalarSerializer<T>(klass) {
  override fun serialize(value: T?, jgen: JsonGenerator, provider: SerializerProvider) {
    value?.let { jgen.writeNumber(converter(it)) }
  }
}
