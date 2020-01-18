package ru.adavliatov.atomy.common.type.ref.impl.json.ext

import ru.adavliatov.atomy.common.type.json.*
import ru.adavliatov.atomy.common.type.ref.*
import ru.adavliatov.atomy.common.type.ref.impl.json.*

object RefExtensions {
    fun <Context: JsonContext> ref(consumer: Json<Context>, ref: Json<Context>? = null) =
        Ref(
            JsonConsumerId(consumer),
            ref?.run { JsonConsumerRef(this) }
        )
}