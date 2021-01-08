package ru.adavliatov.atomy.toolkit.fuel.service.deserialize

import com.github.kittinunf.fuel.core.ResponseDeserializable

typealias Content = String

interface WithFuelViewDeserializer<View : Any> {
  fun deserializeView(content: Content): View

  val viewDeserializer: ResponseDeserializable<View>
    get() = object : ResponseDeserializable<View> {
      override fun deserialize(content: Content): View = deserializeView(content)
    }
}