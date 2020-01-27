package today.selfi.auth.ui.api.exscribe

import com.github.scribejava.apis.GoogleApi20
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import java.io.IOException
import java.util.*
import java.util.concurrent.*

object Google20Example {
  private const val NETWORK_NAME = "Google"
  private const val PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo"
  @Throws(
    IOException::class,
    InterruptedException::class,
    ExecutionException::class
  )
  @JvmStatic
  fun main(args: Array<String>) { // Replace these with your client id and secret
    val clientId = "788673326191-cttogru95u9738b2ml1oovdb6sthvjft.apps.googleusercontent.com"
    val clientSecret = "dbqyLGK-0WbxxwNm7cOmtiZy"
    val secretState = "secret" + Random().nextInt(999999)

    val service = ServiceBuilder(clientId)
      .apiSecret(clientSecret)
      .defaultScope("profile") // replace with desired scope
      .callback("http://selfi.today/oauth/google/callback")
      .build(GoogleApi20.instance())

    val `in` = Scanner(System.`in`, "UTF-8")
    println("=== $NETWORK_NAME's OAuth Workflow ===")
    println()
    // Obtain the Authorization URL
    println("Fetching the Authorization URL...")
    //pass access_type=offline to get refresh token
//https://developers.google.com/identity/protocols/OAuth2WebServer#preparing-to-start-the-oauth-20-flow
    val additionalParams: MutableMap<String, String> =
      HashMap()
    additionalParams["access_type"] = "offline"
    //force to reget refresh token (if user are asked not the first time)
    additionalParams["prompt"] = "consent"

    val authorizationUrl = service.createAuthorizationUrlBuilder()
      .state(secretState)
      .additionalParams(additionalParams)
      .build()

    println("Got the Authorization URL!")
    println("Now go and authorize ScribeJava here:")
    println(authorizationUrl)
    println("And paste the authorization code here")
    print(">>")
    val code = `in`.nextLine()
    println()
    println("And paste the state from server here. We have set 'secretState'='$secretState'.")
    print(">>")
    val value = `in`.nextLine()
    if (secretState == value) {
      println("State value does match!")
    } else {
      println("Ooops, state value does not match!")
      println("Expected = $secretState")
      println("Got      = $value")
      println()
    }
    println("Trading the Authorization Code for an Access Token...")
    var accessToken = service.getAccessToken(code)
    println("Got the Access Token!")
    println("(The raw response looks like this: " + accessToken.rawResponse + "')")
    println("Refreshing the Access Token...")
    accessToken = service.refreshAccessToken(accessToken.refreshToken)
    println("Refreshed the Access Token!")
    println("(The raw response looks like this: " + accessToken.rawResponse + "')")
    println()
    // Now let's go and ask for a protected resource!
    println("Now we're going to access a protected resource...")
    while (true) {
      println("Paste fieldnames to fetch (leave empty to get profile, 'exit' to stop example)")
      print(">>")
      val query = `in`.nextLine()
      println()
      val requestUrl: String
      requestUrl = if ("exit" == query) {
        break
      } else if (query == null || query.isEmpty()) {
        PROTECTED_RESOURCE_URL
      } else {
        "$PROTECTED_RESOURCE_URL?fields=$query"
      }
      val request = OAuthRequest(Verb.GET, requestUrl)
      service.signRequest(accessToken, request)
      println()
      service.execute(request).use { response ->
        println(response.code)
        println(response.body)
      }
      println()
    }
  }
}