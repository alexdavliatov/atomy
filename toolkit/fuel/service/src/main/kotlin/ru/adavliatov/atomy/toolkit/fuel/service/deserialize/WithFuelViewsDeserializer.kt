package ru.adavliatov.atomy.toolkit.fuel.service.deserialize

import com.github.kittinunf.fuel.core.ResponseDeserializable

interface WithFuelViewsDeserializer<View> {
  fun deserializeViews(content: Content): List<View>

  val viewsDeserializer: ResponseDeserializable<List<View>>
    get() = object : ResponseDeserializable<List<View>> {
      override fun deserialize(content: Content): List<View> = deserializeViews(content)
    }
}