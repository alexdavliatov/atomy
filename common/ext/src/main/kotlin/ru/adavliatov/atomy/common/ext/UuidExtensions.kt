package ru.adavliatov.atomy.common.ext

import java.util.UUID

object UuidExtensions {
  fun String.uuid() = UUID.fromString(this)
}