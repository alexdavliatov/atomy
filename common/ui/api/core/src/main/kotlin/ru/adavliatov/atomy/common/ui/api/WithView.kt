package ru.adavliatov.atomy.common.ui.api

interface WithView<View> {
  val viewClass: Class<View>
}