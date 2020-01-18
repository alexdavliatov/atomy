package ru.adavliatov.atomy.common.type.ref.imp.json.ext

import ru.adavliatov.atomy.common.type.json.Json
import ru.adavliatov.atomy.common.type.json.JsonContext
import ru.adavliatov.atomy.common.type.ref.Ref
import ru.adavliatov.atomy.common.type.ref.imp.json.JsonConsumerId
import ru.adavliatov.atomy.common.type.ref.imp.json.JsonConsumerRef

object RefExtensions {
    fun <Context: JsonContext> ref(consumer: Json<Context>, ref: Json<Context>? = null) =
        Ref(
            JsonConsumerId(consumer),
            ref?.run { JsonConsumerRef(this) }
        )
}