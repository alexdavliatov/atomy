package ru.adavliatov.atomy.common.view.plugin.jackson.serializer.ext

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode

object JacksonExtensions {
  fun JsonParser.toNode(): JsonNode = codec.readTree(this)
}