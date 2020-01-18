package today.selfi.shared.ref.ext

import com.fasterxml.jackson.databind.JsonNode
import ru.adavliatov.common.type.json.impl.*
import ru.adavliatov.atomy.common.type.ref.impl.json.ext.RefExtensions.ref as commonRef

object RefExtensions {
  fun ref(consumer: JsonNode, ref: JsonNode? = null) = commonRef(
    JacksonJson(consumer),
    ref?.run { JacksonJson(this) }
  )
}