package ru.adavliatov.atomy.common.view.plugin.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import ru.adavliatov.atomy.common.domain.Id

//todo (adavliatov): move to its own module
class IdSerializer : StdSerializer<Id<*>>(Id::class.java) {
  override fun serialize(value: Id<*>?, gen: JsonGenerator, provider: SerializerProvider) {
    value?.let {
      gen.writeStartObject()
      gen.writeObjectField("uid", value.uid)
      gen.writeObjectField("ref", value.ref)
      gen.writeEndObject()
    }
  }
}
