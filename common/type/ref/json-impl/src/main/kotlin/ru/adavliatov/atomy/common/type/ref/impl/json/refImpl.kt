package ru.adavliatov.atomy.common.type.ref.impl.json

import ru.adavliatov.atomy.common.type.json.Json
import ru.adavliatov.atomy.common.type.json.JsonContext
import ru.adavliatov.atomy.common.type.name.ValueHolder
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.ConsumerRef

class JsonConsumerId<Context : JsonContext>(value: Json<Context>) : ValueHolder<Json<Context>>(value), ConsumerId {
  override fun toString(): String = "JsonConsumerId{$value}"
}

class JsonConsumerRef<Context : JsonContext>(value: Json<Context>) : ValueHolder<Json<Context>>(value), ConsumerRef {
  override fun toString(): String = "JsonConsumerRef{$value}"
}