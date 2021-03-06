package ru.adavliatov.atomy.common.view.plugin.jackson.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import ru.adavliatov.atomy.common.view.plugin.jackson.serializer.ext.JacksonExtensions.toNode

//todo (adavliatov): move to its own module
class DetailsDeserializer<T>(
  val klass: Class<T>,
  val title: String = "title",
  val detailsClassResolver: (String) -> Class<out T>
) :
  StdDeserializer<T>(klass) {
  override fun deserialize(parser: JsonParser, ctx: DeserializationContext): T? {
    val node = parser.toNode()
    return node[title]
      ?.textValue()
      ?.run(detailsClassResolver)
      ?.run { parser.codec.treeToValue(node, this) }
  }
}
