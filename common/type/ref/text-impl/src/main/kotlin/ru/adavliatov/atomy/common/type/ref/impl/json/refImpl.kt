package ru.adavliatov.atomy.common.type.ref.impl.json

import ru.adavliatov.atomy.common.type.name.Value
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.ConsumerRef

@JvmInline
value class TextConsumerId(override val value: String) : ConsumerId, Value<String>
@JvmInline
value class TextConsumerRef(override val value: String) : ConsumerRef, Value<String>