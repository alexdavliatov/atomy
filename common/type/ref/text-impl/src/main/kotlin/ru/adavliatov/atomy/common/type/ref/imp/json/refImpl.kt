package ru.adavliatov.atomy.common.type.ref.imp.json

import ru.adavliatov.atomy.common.type.name.Value
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.ConsumerRef

inline class TextConsumerId(override val value: String) : ConsumerId, Value<String>
inline class TextConsumerRef(override val value: String) : ConsumerRef, Value<String>