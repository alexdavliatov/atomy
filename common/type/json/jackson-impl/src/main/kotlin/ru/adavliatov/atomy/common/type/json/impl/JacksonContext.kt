package ru.adavliatov.atomy.common.type.json.impl

import com.fasterxml.jackson.databind.ObjectMapper
import ru.adavliatov.atomy.common.type.json.*

data class JacksonContext(val mapper: ObjectMapper) : JsonContext