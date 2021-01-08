package ru.adavliatov.atomy.common.type.json.impl

import ru.adavliatov.atomy.common.type.json.WithJsonContext

interface WithJacksonContext : WithJsonContext {
  override val context: JacksonContext
}