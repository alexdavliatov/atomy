package today.selfi.auth.ui.api.expack4j

//import org.pac4j.core.exception.HttpAction
import org.slf4j.LoggerFactory

object JavalinPac4jExample {
  private const val JWT_SALT = "12345678901234567890123456789012"
  private val logger = LoggerFactory.getLogger(JavalinPac4jExample::class.java)
  @JvmStatic
  fun main(args: Array<String>) {
//    val config = ExampleConfigFactory(JWT_SALT).build()
//    val callback = CallbackHandler(config, null, true)
//    val facebookSecurityHandler =
//      SecurityHandler(config, "FacebookClient", "", "excludedPath")
//    Javalin.create()
//      .routes {
//        ApiBuilder.get("/") { obj: Context? -> index() }
//        ApiBuilder.get("/callback", callback)
//        ApiBuilder.post("/callback", callback)
//        ApiBuilder.before("/facebook", facebookSecurityHandler)
//        ApiBuilder.get(
//          "/facebook"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/facebook/*", facebookSecurityHandler)
//        ApiBuilder.get(
//          "/facebook/notprotected"
//        ) { obj: Context? -> protectedPage() } // excluded in ExampleConfigFactory
//        ApiBuilder.before("/facebookadmin", SecurityHandler(config, "FacebookClient", "admin"))
//        ApiBuilder.get(
//          "/facebookadmin"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/facebookcustom", SecurityHandler(config, "FacebookClient", "custom"))
//        ApiBuilder.get(
//          "/facebookcustom"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/twitter", SecurityHandler(config, "TwitterClient,FacebookClient"))
//        ApiBuilder.get(
//          "/twitter"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/form", SecurityHandler(config, "FormClient"))
//        ApiBuilder.get(
//          "/form"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/basicauth", SecurityHandler(config, "IndirectBasicAuthClient"))
//        ApiBuilder.get(
//          "/basicauth"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/cas", SecurityHandler(config, "CasClient"))
//        ApiBuilder.get(
//          "/cas"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/saml2", SecurityHandler(config, "SAML2Client"))
//        ApiBuilder.get(
//          "/saml2"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/oidc", SecurityHandler(config, "OidcClient"))
//        ApiBuilder.get(
//          "/oidc"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/protected", SecurityHandler(config, null))
//        ApiBuilder.get(
//          "/protected"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/dba", SecurityHandler(config, "DirectBasicAuthClient,ParameterClient"))
//        ApiBuilder.get(
//          "/dba"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.before("/rest-jwt", SecurityHandler(config, "ParameterClient"))
//        ApiBuilder.get(
//          "/rest-jwt"
//        ) { obj: Context? -> protectedPage() }
//        ApiBuilder.get("/jwt") { obj: Context? -> jwt() }
//        ApiBuilder.get(
//          "/login-form"
//        ) { ctx: Context -> form(ctx, config) }
//        ApiBuilder.get("/logout", localLogoutHandler(config))
//        ApiBuilder.get("/central-logout", centralLogoutHandler(config))
//        ApiBuilder.get(
//          "/force-login"
//        ) { ctx: Context -> forceLogin(ctx, config) }
//        ApiBuilder.before("/body", SecurityHandler(config, "HeaderClient"))
//        ApiBuilder.post("/body") { ctx: Context ->
//          logger.debug("Body: " + ctx.body())
//          ctx.result("done: " + getProfiles(ctx))
//        }
//      }.exception(
//        Exception::class.java
//      ) { e: Exception, ctx: Context ->
//        logger.error("Unexpected exception", e)
//        ctx.result(e.toString())
//      }.start(8080)
  }
//
//  private fun centralLogoutHandler(config: Config?): LogoutHandler {
//    val centralLogout = LogoutHandler(config)
//    centralLogout.defaultUrl = "http://localhost:8080/?defaulturlafterlogoutafteridp"
//    centralLogout.logoutUrlPattern = "http://localhost:8080/.*"
//    centralLogout.localLogout = false
//    centralLogout.centralLogout = true
//    centralLogout.destroySession = true
//    return centralLogout
//  }
//
//  private fun localLogoutHandler(config: Config?): LogoutHandler {
//    val localLogout =
//      LogoutHandler(config, "/?defaulturlafterlogout")
//    localLogout.destroySession = true
//    return localLogout
//  }
//
//  private fun index(ctx: Context?) {
//    ctx?.render("/templates/index.vm", model("profiles", getProfiles(ctx)))
//  }
//
//  private fun jwt(ctx: Context) {
//    val context = Pac4jContext(ctx)
//    val manager: ProfileManager<*> = ProfileManager<Any?>(context)
//    val profile: Optional<CommonProfile> = manager[true]
//    var token = ""
//    if (profile.isPresent) {
//      val generator: JwtGenerator<*> =
//        JwtGenerator<Any?>(SecretSignatureConfiguration(JWT_SALT))
//      token = generator.generate(profile.get())
//    }
//    ctx.render("/templates/jwt.vm", model("token", token))
//  }
//
//  private fun form(ctx: Context, config: Config?) {
//    val formClient: FormClient = config!!.clients.findClient(FormClient::class.java)
//    ctx.render("/templates/loginForm.vm", model("callbackUrl", formClient.callbackUrl))
//  }
//
//  private fun protectedPage(ctx: Context) {
//    ctx.render("/templates/protectedPage.vm", model("profiles", getProfiles(ctx)))
//  }
//
//  private fun getProfiles(ctx: Context): List<CommonProfile?> {
//    return ProfileManager<Any?>(Pac4jContext(ctx)).getAll(true)
//  }
//
//  private fun forceLogin(ctx: Context, config: Config?) {
//    val context = Pac4jContext(ctx)
//    val clientName: String = context.getRequestParameter("FormClient")
//    val client: Client<*> = config!!.clients.findClient(clientName)
//    var action: HttpAction
//    try {
//      action = client.redirect(context)
//    } catch (e: HttpAction) {
//      action = e
//    }
//    config.httpActionAdapter.adapt(action.getCode(), context)
//  }
}