package ru.adavliatov.atomy.common.view.plugin.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer

//todo (adavliatov): move to its own module
class AsStringSerializer<T>(klass: Class<T>, val asString: (T) -> String) : StdScalarSerializer<T>(klass) {
  override fun serialize(value: T?, jgen: JsonGenerator, provider: SerializerProvider) {
    value?.let { jgen.writeString(asString(it)) }
  }
}
