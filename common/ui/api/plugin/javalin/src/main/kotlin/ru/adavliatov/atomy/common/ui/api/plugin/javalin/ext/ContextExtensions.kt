package ru.adavliatov.atomy.common.ui.api.plugin.javalin.ext

import io.javalin.http.Context

object ContextExtensions {
  fun Context.consumer() = header("consumer") ?: "common"
//    ?: throw InvalidArgumentError(message = "No consumer id provided").toHttpError()
}