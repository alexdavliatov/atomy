package ru.adavliatov.atomy.common.ui.api.plugin.javalin

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.delete
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.patch
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.core.security.SecurityUtil.roles
import io.javalin.http.Context
import io.javalin.plugin.openapi.dsl.OpenApiDocumentation
import io.javalin.plugin.openapi.dsl.createUpdater
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import ru.adavliatov.atomy.common.ext.UuidExtensions.uuid
import ru.adavliatov.atomy.common.ext.validateNotNull
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.type.page.SortDirection
import ru.adavliatov.atomy.common.type.page.SortDirection.DESC
import ru.adavliatov.atomy.common.ui.api.CommonController
import ru.adavliatov.atomy.common.ui.api.Prop
import ru.adavliatov.atomy.common.ui.api.WithChunked
import ru.adavliatov.atomy.common.ui.api.WithModify
import ru.adavliatov.atomy.common.ui.api.WithMultiple
import ru.adavliatov.atomy.common.ui.api.WithNew
import ru.adavliatov.atomy.common.ui.api.WithNews
import ru.adavliatov.atomy.common.ui.api.WithOne
import ru.adavliatov.atomy.common.ui.api.WithRemove
import ru.adavliatov.atomy.common.ui.api.WithView
import ru.adavliatov.atomy.common.ui.api.domain.ConsumerAuth
import ru.adavliatov.atomy.common.ui.api.domain.MissingAuth
import ru.adavliatov.atomy.common.ui.api.domain.PrincipalRolesAuth
import ru.adavliatov.atomy.common.ui.api.domain.Request
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.WithAuth
import ru.adavliatov.atomy.common.ui.api.domain.error.ApiError
import ru.adavliatov.atomy.common.ui.api.domain.error.PermissionDeniedError
import ru.adavliatov.atomy.common.ui.api.domain.error.StatusCode
import ru.adavliatov.atomy.common.ui.api.domain.error.toHttpErrorView
import ru.adavliatov.atomy.common.ui.api.plugin.javalin.JavalinExtensions.toCommonCtx
import ru.adavliatov.atomy.common.ui.api.plugin.javalin.auth.ApiRole.developer
import ru.adavliatov.atomy.common.ui.api.view.ApiErrorView
import java.util.UUID
import ru.adavliatov.atomy.common.ui.api.domain.Context as CommonContext

interface CommonJavalinController<Id, Model, View> : CommonController<Id, Model, View>,
  WithJavalinNew<Id, View>,
  WithJavalinNews<Id, View>,
  WithJavalinModify<Id, View>,
  WithJavalinRemove<Id, View>,
  WithJavalinOne<Id, Model, View>,
  WithJavalinMultiple<Id, Model, View>,
  WithJavalinChunked<Id, Model, View>,
  WithJavalinAuth {

  fun routes(url: String) = { javalin: Javalin ->
    javalin.routes {
      path(url) {
        post(documented(newsDoc, this::newsRoute), roles(developer))
        post("multiple", documented(multipleDoc, this::multipleRoute), roles(developer))
        get(documented(chunkedDoc, this::chunkedRoute), roles(developer))
        path(":id") {
          patch(documented(modifyDoc, this::modifyRoute), roles(developer))
          delete(documented(removeDoc, this::removeRoute), roles(developer))
          get(documented(oneDoc, this::oneRoute), roles(developer))
        }
      }
    }
  }

}

interface WithJavalinAuth {
  fun Context.auth(): WithAuth

  fun Context.toCtx(): CommonContext = toCommonCtx(auth())
}

interface WithJavalinPrincipalRolesAuth : WithJavalinAuth {
  override fun Context.auth(): PrincipalRolesAuth {
    val auth = attribute<PrincipalRolesAuth>("auth")
    validateNotNull(auth) { PermissionDeniedError(message = "Missing auth info") }

    return auth
  }
}

interface WithJavalinMissingAuth : WithJavalinAuth {
  override fun Context.auth(): MissingAuth = MissingAuth
}

interface WithJavalinConsumerAuth : WithJavalinAuth {
  override fun Context.auth(): ConsumerAuth {
    val auth = attribute<ConsumerAuth>("auth")
    validateNotNull(auth) { PermissionDeniedError(message = "Missing auth info") }

    return auth
  }
}

interface WithJavalinProps {
  fun Context.props(): List<Prop> = queryParams("props")
}

interface WithJavalinChunk {
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
  fun Context.views(): List<View>
}

interface WithJavalinCtxError {
  fun Context.error(error: ApiError) = status(error.httpStatus).json(error.toHttpErrorView())
}

interface WithJavalinId<Id> {
  fun Context.id(): Id
}

interface WithJavalinUUID : WithJavalinId<UUID> {
  override fun Context.id(): UUID = pathParam("id").uuid()
}

interface WithJavalinUUIDs : WithJavalinIds<UUID> {
  override fun Context.ids(): List<UUID> = bodyAsClass(Array<UUID>::class.java).map { it }
}

interface WithJavalinIds<Id> {
  fun Context.ids(): List<Id>
}

interface WithJavalinNew<Id, View> : WithNew<Id, View>,
  WithJavalinAuth,
  WithJavalinCtxToView<View>,
  WithDoc {
  val newDoc: OpenApiDocumentation
    get() = rootDoc()
      .operation { it.summary = "Creates model" }
      .body(viewClass) { it.description = "View" }
      .jsonArray<String>("204") { it.description = "Created model id" }
      .result<ApiErrorView>("500") { it.description = "Internal error" }

  fun newRoute(ctx: Context) {
    newRoute(ctx.toCtx(), ctx.view())
  }
}

interface WithJavalinNews<Id, View> : WithNews<Id, View>,
  WithJavalinAuth,
  WithJavalinCtxToViews<View>,
  WithDoc {
  val newsDoc: OpenApiDocumentation
    get() = rootDoc()
      .operation { it.summary = "Creates multiple models" }
      .body(viewClass) { it.description = "List(!!!) of View" }
      .jsonArray<UUID>("200") {
        it.description = "View ids (UUID) in the same order"
      }
      .result<ApiErrorView>("500") { it.description = "Internal error" }

  fun newsRoute(ctx: Context) {
    newsRoute(ctx.toCtx(), ctx.views())
  }
}

interface WithJavalinModify<Id, View> : WithModify<Id, View>,
  WithJavalinId<Id>,
  WithJavalinAuth,
  WithJavalinCtxToView<View>,
  WithDoc {
  val modifyDoc: OpenApiDocumentation
    get() = rootDoc()
      .operation { it.summary = "Modifies existing model" }
      .pathParam<UUID>("id") { it.description = "View id" }
      .body(viewClass) { it.description = "View" }
      .result<Unit>("204") { it.description = "Successful operation" }
      .result<ApiErrorView>("500") { it.description = "Internal error" }

  fun modifyRoute(ctx: Context) {
    modifyRoute(ctx.toCtx(), ctx.id(), ctx.view())
  }
}

interface WithJavalinRemove<Id, View> : WithRemove<Id>,
  WithJavalinId<Id>,
  WithJavalinAuth,
  WithJavalinCtxToView<View>,
  WithDoc {
  val removeDoc: OpenApiDocumentation
    get() = rootDoc()
      .operation { it.summary = "Removes existing model" }
      .pathParam<UUID>("id") { it.description = "View id" }
      .result<Unit>("204") { it.description = "Successful operation" }
      .result<ApiErrorView>("500") { it.description = "Internal error" }

  fun removeRoute(ctx: Context) {
    remove(ctx.toCtx(), ctx.id())
  }
}

interface WithJavalinOne<Id, Model, View> : WithOne<Id, Model, View>,
  WithJavalinId<Id>,
  WithJavalinCtxToView<View>,
  WithJavalinAuth,
  WithJavalinProps,
  WithDoc {
  val oneDoc: OpenApiDocumentation
    get() = rootDoc()
      .operation { it.summary = "Returns existing model's view" }
      .pathParam<UUID>("id") { it.description = "View id" }
      .json("200", viewClass, createUpdater { it.description = "View by id" })
      .result<ApiErrorView>("404") { it.description = "View not found" }
      .result<ApiErrorView>("500") { it.description = "Internal error" }

  fun oneRoute(ctx: Context) {
    oneRoute(ctx.toCtx(), ctx.id(), ctx.props())
  }
}

interface WithJavalinMultiple<Id, Model, View> : WithMultiple<Id, Model, View>,
  WithJavalinIds<Id>,
  WithJavalinCtxToView<View>,
  WithJavalinAuth,
  WithJavalinChunk,
  WithDoc {
  val multipleDoc: OpenApiDocumentation
    get() = rootDoc()
      .operation { it.summary = "Returns multiple views by ids" }
      .body(Array<UUID>::class.java) { it.description = "Ids" }
      .jsonArray("200", viewClass, createUpdater { it.description = "Multiple views" })
      .result<ApiErrorView>("500") { it.description = "Internal error" }

  fun multipleRoute(ctx: Context) {
    multipleRoute(ctx.toCtx(), ctx.page(), ctx.ids())
  }
}

interface WithJavalinChunked<Id, Model, View> : WithChunked<Model, View>,
  WithJavalinIds<Id>,
  WithJavalinCtxToView<View>,
  WithJavalinAuth,
  WithJavalinChunk,
  WithDoc {
  val chunkedDoc: OpenApiDocumentation
    get() = rootDoc()
      .operation { it.summary = "Returns paginated models' views" }
      .queryParam<Int>("limit") { it.description = "limit" }
      .queryParam<Int>("offset") { it.description = "offset" }
      .queryParam<String>("sortDirection") { it.description = "sortDirection: DESC|ASC" }
      .queryParam<String>("props") { it.description = "List of requested properties" }
      .queryParam<String>("sortBy") { it.description = "Sort by property" }
      .queryParam<String>("searchBy") { it.description = "Search by property" }
      .queryParam<String>("searchField") { it.description = "Search field property" }
      .jsonArray("200", viewClass, createUpdater { it.description = "Multiple models by ids" })
      .result<ApiErrorView>("500") { it.description = "Internal error" }

  fun chunkedRoute(ctx: Context) {
    chunkedRoute(ctx.toCtx(), ctx.page())
  }
}

interface WithDoc {
  val doc: OpenApiDocumentation

  fun rootDoc() = document()
    .apply { apply(doc) }
}

object JavalinExtensions {
  fun Context.request() = object : Request {
    override fun header(name: String): String? = this@request.header(name)
  }

  fun Context.response() = ContextResponseWrapper(this)

  fun Context.toCommonCtx(auth: WithAuth) = CommonContext(
    request(),
    response(),
    auth,
    attributeMap()
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