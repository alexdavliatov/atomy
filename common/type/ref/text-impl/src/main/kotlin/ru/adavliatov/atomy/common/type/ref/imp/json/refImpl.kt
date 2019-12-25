package ru.adavliatov.atomy.common.type.ref.imp.json

import ru.adavliatov.atomy.common.type.name.*
import ru.adavliatov.atomy.common.type.ref.*

inline class TextConsumerId(override val value: String) : ConsumerId, Value<String>
inline class TextConsumerRef(override val value: String) : ConsumerRef, Value<String>