package today.selfi.auth.ui.api.expack4j

import org.pac4j.core.authorization.authorizer.ProfileAuthorizer
import org.pac4j.core.context.WebContext
import org.pac4j.core.exception.http.HttpAction
import org.pac4j.core.profile.CommonProfile

class CustomAuthorizer : ProfileAuthorizer<CommonProfile?>() {
  @Throws(HttpAction::class)
  override fun isAuthorized(context: WebContext?, profiles: MutableList<CommonProfile?>?) =
    isAnyAuthorized(context, profiles)

  public override fun isProfileAuthorized(context: WebContext, profile: CommonProfile?) =
    profile?.username?.startsWith("jle") ?: false
}