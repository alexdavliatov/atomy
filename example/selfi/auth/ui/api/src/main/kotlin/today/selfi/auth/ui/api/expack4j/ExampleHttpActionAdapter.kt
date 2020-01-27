package today.selfi.auth.ui.api.expack4j

import io.javalin.http.ForbiddenResponse
import io.javalin.http.UnauthorizedResponse
import org.pac4j.core.context.HttpConstants
import org.pac4j.core.exception.http.HttpAction
import org.pac4j.javalin.DefaultHttpActionAdapter
import org.pac4j.javalin.Pac4jContext

class ExampleHttpActionAdapter : DefaultHttpActionAdapter() {
  override fun adapt(action: HttpAction?, context: Pac4jContext?): Void {
    val code: Int = action?.code!!
    return if (code == HttpConstants.UNAUTHORIZED) {
      throw UnauthorizedResponse()
    } else if (code == HttpConstants.FORBIDDEN) {
      throw ForbiddenResponse()
    } else {
      super.adapt(code, context)
    }
  }
}