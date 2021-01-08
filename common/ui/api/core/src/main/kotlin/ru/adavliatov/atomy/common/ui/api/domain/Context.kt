package ru.adavliatov.atomy.common.ui.api.domain

data class Context(
  val request: Request,
  val response: Response,
  val auth: WithAuth,
  val attributes: Map<String, Any?> = mapOf()
)
