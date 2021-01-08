package ru.adavliatov.atomy.common.type.json.impl

import ru.adavliatov.atomy.common.type.json.WithJsonContext

interface WithGsonContext : WithJsonContext {
  override val context: GsonContext
}