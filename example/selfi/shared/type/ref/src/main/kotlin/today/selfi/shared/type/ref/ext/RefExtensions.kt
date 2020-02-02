package today.selfi.shared.type.ref.ext

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.TextNode
import ru.adavliatov.atomy.common.type.json.impl.*
import ru.adavliatov.atomy.common.type.ref.impl.json.*
import ru.adavliatov.atomy.common.type.ref.impl.json.ext.RefExtensions.ref as commonRef

object RefExtensions {
  fun consumer(node: JsonNode) = JsonConsumerId(
    JacksonJson(node)
  )

  fun consumer(consumerId: String) = JsonConsumerId(
    JacksonJson(TextNode.valueOf(consumerId))
  )

  fun ref(consumer: JsonNode, ref: JsonNode? = null) = commonRef(
    JacksonJson(consumer),
    ref?.run { JacksonJson(this) }
  )
}