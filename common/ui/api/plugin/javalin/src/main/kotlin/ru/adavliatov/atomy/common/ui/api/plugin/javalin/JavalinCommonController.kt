package ru.adavliatov.atomy.common.ui.api.plugin.javalin

import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context
import ru.adavliatov.atomy.common.ui.api.CommonController
import ru.adavliatov.atomy.common.ui.api.WithMultiple
import ru.adavliatov.atomy.common.ui.api.WithNew
import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Request
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCode
import ru.adavliatov.atomy.common.ui.api.domain.Context as CommonContext

interface JavalinCommonController<Id, Model, View> : CommonController<Id, Model, View> {
  val routes: EndpointGroup
    get() = EndpointGroup {

    }
}

interface WithJavalin {
  fun route(ctx: Context)
}

interface JavalinWithNew<Id, View> : WithJavalin, WithNew<Id, View>

interface JavalinWithMultiple<Id, Model, View> : WithJavalin, WithMultiple<Id, Model, View>

object JavalinExtensions {
  fun request() = object : Request {}
  fun Context.response() = ContextResponseWrapper(this)

  object NoAuth : Auth

  fun Context.toCommonContext() = CommonContext(
    request(),
    response(),
    NoAuth
  )
}

data class ContextResponseWrapper(val context: Context) : Response {
  override val statusCode: StatusCode
    get() = context.status()
  override val body: Any?
    get() = context.body()

  override fun withStatusCode(statusCode: StatusCode): Response = copy(context = context.status(statusCode))

  //fixme adavliatov: add ObjectMapper here
  override fun <T> withBody(body: T): Response = copy(context = context.json(body as Any))
}