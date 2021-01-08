package ru.adavliatov.atomy.toolkit.fuel.service.deserialize

import com.github.kittinunf.fuel.core.ResponseDeserializable

interface WithFuelIdDeserializer<Id : Any> {
  fun deserializeIds(content: Content): List<Id>

  val idsDeserializer: ResponseDeserializable<List<Id>>
    get() = object : ResponseDeserializable<List<Id>> {
      override fun deserialize(content: Content): List<Id> = deserializeIds(content)
    }
}