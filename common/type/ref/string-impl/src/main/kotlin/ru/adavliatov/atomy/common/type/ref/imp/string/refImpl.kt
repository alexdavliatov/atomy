package ru.adavliatov.atomy.common.type.ref.imp.string

import ru.adavliatov.atomy.common.type.name.Value
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.ConsumerRef

inline class StringConsumerId(override val value: String) : ConsumerId, Value<String>
inline class StringConsumerRef(override val value: String) : ConsumerRef, Value<String>