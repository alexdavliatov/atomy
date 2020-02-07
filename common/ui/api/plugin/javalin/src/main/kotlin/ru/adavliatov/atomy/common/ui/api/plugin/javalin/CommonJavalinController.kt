package ru.adavliatov.atomy.common.ui.api.plugin.javalin

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.delete
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.patch
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import ru.adavliatov.atomy.common.ext.UuidExtensions.uuid
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.type.page.SortDirection
import ru.adavliatov.atomy.common.type.page.SortDirection.DESC
import ru.adavliatov.atomy.common.ui.api.CommonController
import ru.adavliatov.atomy.common.ui.api.Prop
import ru.adavliatov.atomy.common.ui.api.WithModify
import ru.adavliatov.atomy.common.ui.api.WithMultiple
import ru.adavliatov.atomy.common.ui.api.WithNew
import ru.adavliatov.atomy.common.ui.api.WithNews
import ru.adavliatov.atomy.common.ui.api.WithOne
import ru.adavliatov.atomy.common.ui.api.WithPaginated
import ru.adavliatov.atomy.common.ui.api.WithRemove
import ru.adavliatov.atomy.common.ui.api.WithView
import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Request
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCode
import ru.adavliatov.atomy.common.ui.api.plugin.javalin.JavalinExtensions.toCommonCtx
import java.util.UUID
import ru.adavliatov.atomy.common.ui.api.domain.Context as CommonContext

interface CommonJavalinController<Id, Model, View> : CommonController<Id, Model, View>,
  WithJavalinNew<Id, View>,
  WithJavalinNews<Id, View>,
  WithJavalinModify<Id, View>,
  WithJavalinRemove<Id, View>,
  WithJavalinOne<Id, Model, View>,
  WithJavalinMultiple<Id, Model, View>,
  WithJavalinPaginated<Id, Model, View>,
  WithJavalinAuth {

  fun routes(url: String) = { javalin: Javalin ->
    javalin.routes {
      path(url) {
        post(this::newRoute)
        get("multiple", this::multipleRoute)
        get(this::paginatedRoute)
        path(":id") {
          patch(this::modifyRoute)
          delete(this::removeRoute)
          get(this::oneRoute)
        }
      }
    }
  }

}

interface WithJavalinAuth {
  fun Context.auth(): Auth

  fun Context.toCtx(): CommonContext = toCommonCtx(auth())
}

interface WithJavalinProps {
  fun Context.props(): List<Prop> = queryParams("props")
}

interface WithJavalinPage {
  fun Context.page(): Page = Page(
    queryParam("limit")?.toInt() ?: 50,
    queryParam("offset")?.toInt() ?: 0,
    queryParam("sortDirection")?.let { SortDirection.valueOf(it) } ?: DESC,
    queryParams("props").toSet(),
    queryParam("sortBy"),
    queryParam("searchBy"),
    queryParam("searchField")
  )
}

interface WithJavalinCtxToView<View> : WithView<View> {
  fun Context.view(): View = bodyAsClass(viewClass)
}

interface WithJavalinCtxToViews<View> : WithView<View> {
  fun Context.views(): Set<View>
}

interface WithJavalinId<Id> {
  fun Context.id(): Id
}

interface WithJavalinUUID : WithJavalinId<UUID> {
  override fun Context.id(): UUID = pathParam("id").uuid()
}

interface WithJavalinUUIDs : WithJavalinIds<UUID> {
  override fun Context.ids(): List<UUID> = queryParams("ids").map { it.uuid() }
}

interface WithJavalinIds<Id> {
  fun Context.ids(): List<Id>
}

interface WithJavalinNew<Id, View> : WithNew<Id, View>,
  WithJavalinAuth,
  WithJavalinCtxToView<View> {
  fun newRoute(ctx: Context) {
    newRoute(ctx.toCtx(), ctx.view())
  }
}

interface WithJavalinNews<Id, View> : WithNews<Id, View>,
  WithJavalinAuth,
  WithJavalinCtxToViews<View> {
  fun newsRoute(ctx: Context) {
    newsRoute(ctx.toCtx(), ctx.views())
  }
}

interface WithJavalinModify<Id, View> : WithModify<Id, View>,
  WithJavalinId<Id>,
  WithJavalinAuth,
  WithJavalinCtxToView<View> {
  fun modifyRoute(ctx: Context) {
    modifyRoute(ctx.toCtx(), ctx.id(), ctx.view())
  }
}

interface WithJavalinRemove<Id, View> : WithRemove<Id>,
  WithJavalinId<Id>,
  WithJavalinAuth,
  WithJavalinCtxToView<View> {
  fun removeRoute(ctx: Context) {
    remove(ctx.toCtx(), ctx.id())
  }
}

interface WithJavalinOne<Id, Model, View> : WithOne<Id, Model, View>,
  WithJavalinId<Id>,
  WithJavalinCtxToView<View>,
  WithJavalinAuth,
  WithJavalinProps {
  fun oneRoute(ctx: Context) {
    oneRoute(ctx.toCtx(), ctx.id(), ctx.props())
  }
}

interface WithJavalinMultiple<Id, Model, View> : WithMultiple<Id, Model, View>,
  WithJavalinIds<Id>,
  WithJavalinCtxToView<View>,
  WithJavalinAuth,
  WithJavalinPage {
  fun multipleRoute(ctx: Context) {
    multipleRoute(ctx.toCtx(), ctx.page(), ctx.ids())
  }
}

interface WithJavalinPaginated<Id, Model, View> : WithPaginated<Model, View>,
  WithJavalinIds<Id>,
  WithJavalinCtxToView<View>,
  WithJavalinAuth,
  WithJavalinPage {
  fun paginatedRoute(ctx: Context) {
    paginatedRoute(ctx.toCtx(), ctx.page())
  }
}

object JavalinExtensions {
  fun request() = object : Request {}
  fun Context.response() = ContextResponseWrapper(this)

  object NoAuth : Auth

  fun Context.toCommonCtx(auth: Auth) = CommonContext(
    request(),
    response(),
    auth
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