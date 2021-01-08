package ru.adavliatov.atomy.common.view.plugin.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import ru.adavliatov.atomy.common.type.ref.Ref

//todo (adavliatov): move to its own module
object RefSerializer : StdSerializer<Ref>(Ref::class.java) {
  override fun serialize(value: Ref?, gen: JsonGenerator?, provider: SerializerProvider?) {

  }
}
