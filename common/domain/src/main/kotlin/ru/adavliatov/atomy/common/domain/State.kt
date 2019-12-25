package ru.adavliatov.atomy.common.domain

import ru.adavliatov.atomy.common.domain.error.DomainErrors.InvalidStateNameError
import ru.adavliatov.atomy.common.ext.ValidationExtensions

data class State(val name: String) {
  init {
    ValidationExtensions.validate(name.isNotBlank()) { InvalidStateNameError }
  }
}

@Suppress("unused")
object States {
  val ACTIVE = State("active")
  val DELETED = State("deleted")
  val NONE = State("none")
}
