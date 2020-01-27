package today.selfi.auth.ui.api.expack4j

import org.pac4j.core.context.WebContext
import org.pac4j.core.credentials.UsernamePasswordCredentials
import org.pac4j.core.credentials.authenticator.Authenticator
import org.pac4j.core.exception.CredentialsException
import org.pac4j.core.profile.CommonProfile

class TrivialUserPassAuthenticator(
  private val testUsername: String,
  private val testPassword: String
) : Authenticator<UsernamePasswordCredentials> {

  override fun validate(credentials: UsernamePasswordCredentials, context: WebContext) =
    if (testUsername == credentials.username
      && testPassword == credentials.password
    ) {
      val profile = CommonProfile()
      profile.id = credentials.username
      profile.addAttribute("username", credentials.username)
      credentials.userProfile = profile
    } else throw CredentialsException("Invalid credentials")

}