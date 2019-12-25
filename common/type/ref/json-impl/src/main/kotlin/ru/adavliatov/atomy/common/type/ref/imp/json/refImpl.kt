package ru.adavliatov.atomy.common.type.ref.imp.json

import ru.adavliatov.atomy.common.type.json.*
import ru.adavliatov.atomy.common.type.name.*
import ru.adavliatov.atomy.common.type.ref.*

class JsonConsumerId<Context : JsonContext>(value: Json<Context>) : ValueHolder<Json<Context>>(value), ConsumerId
class JsonConsumerRef<Context : JsonContext>(value: Json<Context>) : ValueHolder<Json<Context>>(value), ConsumerRef