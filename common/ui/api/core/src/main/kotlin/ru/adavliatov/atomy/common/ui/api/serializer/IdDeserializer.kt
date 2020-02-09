package ru.adavliatov.atomy.common.ui.api.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.MissingNode
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.ext.UuidExtensions.uuid
import ru.adavliatov.atomy.common.type.ref.Ref
import ru.adavliatov.atomy.common.ui.api.ext.RefExtensions.ref

//todo (adavliatov): move to its own module
object IdDeserializer : StdDeserializer<Id<*>>(Id::class.java) {
  override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Id<*> {
    val codec = parser.codec
    val node = codec.readTree<JsonNode>(parser)
    val id = node["id"]?.asLong()
    val uid = node["uid"]?.asText()?.uuid()
    val ref = node["ref"]?.run { codec.treeToValue(this, Ref::class.java) } ?: ref(MissingNode.getInstance())

    return Id(id, uid, ref, Nothing::class.java)
  }
}
