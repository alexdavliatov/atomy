package today.selfi.auth.ui.api.exscribe

import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import java.io.IOException
import java.util.Scanner
import java.util.concurrent.ExecutionException

object TwitterExample {
  private const val PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json"
  @Throws(
    IOException::class,
    InterruptedException::class,
    ExecutionException::class
  )
  @JvmStatic
  fun main(args: Array<String>) {
    val service = ServiceBuilder("your client id")
      .apiSecret("your client secret")
      .build(TwitterApi.instance())

    val `in` = Scanner(System.`in`)
    println("=== Twitter's OAuth Workflow ===")
    println()
    // Obtain the Request Token
    println("Fetching the Request Token...")
    val requestToken = service.requestToken
    println("Got the Request Token!")
    println()
    println("Now go and authorize ScribeJava here:")
    println(service.getAuthorizationUrl(requestToken))
    println("And paste the verifier here")
    print(">>")
    val oauthVerifier = `in`.nextLine()
    println()
    // Trade the Request Token and Verfier for the Access Token
    println("Trading the Request Token for an Access Token...")
    val accessToken = service.getAccessToken(requestToken, oauthVerifier)
    println("Got the Access Token!")
    println("(The raw response looks like this: " + accessToken.rawResponse + "')")
    println()
    // Now let's go and ask for a protected resource!
    println("Now we're going to access a protected resource...")
    val request = OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL)
    service.signRequest(accessToken, request)
    service.execute(request).use { response ->
      println("Got it! Lets see what we found...")
      println()
      println(response.body)
    }
    println()
    println("That's it man! Go and build something awesome with ScribeJava! :)")
  }
}