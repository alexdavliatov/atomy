package ru.adavliatov.atomy.common.ui.api.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import ru.adavliatov.atomy.common.type.ref.Ref
import ru.adavliatov.atomy.common.ui.api.ext.RefExtensions.ref
import ru.adavliatov.atomy.common.ui.api.serializer.ext.JacksonExtensions.toNode

//todo (adavliatov): move to its own module
object RefDeserializer : StdDeserializer<Ref>(Ref::class.java) {
  override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Ref {
    val node = parser.toNode()
    val consumer = node["consumer"]
    val ref = node["ref"]

    return ref(consumer, ref)
  }
}
