package today.selfi.auth.ui.api.expack4j

import org.pac4j.core.config.Config
import org.pac4j.core.config.ConfigFactory

class ExampleConfigFactory(private val salt: String) : ConfigFactory {
  override fun build(vararg parameters: Any): Config {
//    val oidcConfiguration = OidcConfiguration()
//    oidcConfiguration.clientId = "343992089165-sp0l1km383i8cbm2j5nn20kbk5dk8hor.apps.googleusercontent.com"
//    oidcConfiguration.secret = "uR3D8ej1kIRPbqAFaxIE3HWh"
//    oidcConfiguration.discoveryURI = "https://accounts.google.com/.well-known/openid-configuration"
//    oidcConfiguration.isUseNonce = true
//    oidcConfiguration.addCustomParam("prompt", "consent")
//    val oidcClient: OidcClient<*> = OidcClient<Any?>(oidcConfiguration)
//    oidcClient.setAuthorizationGenerator { ctx: WebContext?, profile: UserProfile ->
//      profile.addRole("ROLE_ADMIN")
//      profile
//    }
//    val cfg = SAML2ClientConfiguration(
//      "resource:samlKeystore.jks",
//      "pac4j-demo-passwd",
//      "pac4j-demo-passwd",
//      "resource:metadata-okta.xml"
//    )
//    cfg.setMaximumAuthenticationLifetime(3600)
//    cfg.setServiceProviderEntityId("http://localhost:8080/callback?client_name=SAML2Client")
//    cfg.setServiceProviderMetadataPath("sp-metadata.xml")
//    val saml2Client = SAML2Client(cfg)
//    val facebookClient = FacebookClient("145278422258960", "be21409ba8f39b5dae2a7de525484da8")
//    val twitterClient =
//      TwitterClient("CoxUiYwQOSFDReZYdjigBA", "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs")
//    val trivialUserPassAuthenticator =
//      TrivialUserPassAuthenticator("testUsername", "testPassword")
//    // HTTP
//    val formClient = FormClient("http://localhost:8080/login-form", trivialUserPassAuthenticator)
//    val indirectBasicAuthClient = IndirectBasicAuthClient(trivialUserPassAuthenticator)
//    // CAS
//    val casConfiguration = CasConfiguration("https://casserverpac4j.herokuapp.com/login")
//    val casClient = CasClient(casConfiguration)
//    // REST authent with JWT for a token passed in the url as the token parameter
//    val parameterClient =
//      ParameterClient("token", JwtAuthenticator(SecretSignatureConfiguration(salt)))
//    parameterClient.isSupportGetRequest = true
//    parameterClient.isSupportPostRequest = false
//    // basic auth
//    val directBasicAuthClient = DirectBasicAuthClient(trivialUserPassAuthenticator)
//    val headerClient = HeaderClient(
//      "Authorization",
//      Authenticator<*> { credentials: Credentials, ctx: WebContext? ->
//        val token = (credentials as TokenCredentials).token
//        if (CommonHelper.isNotBlank(token)) {
//          val profile = CommonProfile()
//          profile.id = token
//          credentials.setUserProfile(profile)
//        }
//      }
//    )
//    val clients = Clients(
//      "http://localhost:8080/callback",
//      oidcClient,
//      saml2Client,
//      facebookClient,
//      twitterClient,
//      formClient,
//      indirectBasicAuthClient,
//      casClient,
//      parameterClient,
//      directBasicAuthClient,
//      AnonymousClient(),
//      headerClient
//    )
//    val config = Config(clients)
//    config.addAuthorizer("admin", RequireAnyRoleAuthorizer<Any?>("ROLE_ADMIN"))
//    config.addAuthorizer("custom", CustomAuthorizer())
//    config.addMatcher("excludedPath", PathMatcher().excludeRegex("^/facebook/notprotected$"))
//    config.httpActionAdapter = ExampleHttpActionAdapter()
//    return config
    return Config()
  }

}