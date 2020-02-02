package today.selfi.auth.app

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.plugin.json.JavalinJackson
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.core.logger.Level.DEBUG
import org.koin.dsl.module
import org.slf4j.LoggerFactory
import ru.adavliatov.atomy.common.ui.api.serializer.*
import today.selfi.auth.app.config.AppConfig
import today.selfi.auth.app.config.AppConfigs
import today.selfi.auth.app.config.DataSourceWrapper
import today.selfi.auth.app.config.DbConfigs
import today.selfi.auth.app.config.Environment
import today.selfi.auth.app.config.Environment.DEV
import today.selfi.auth.service.repo.AccountJooqRepo
import today.selfi.auth.ui.api.AuthRoutes
import today.selfi.item.service.repo.ItemRepo
import javax.sql.DataSource

class AuthApp : KoinComponent {
  private val appConfig by inject<AppConfig>()
  private val authRoutes by inject<AuthRoutes>()

  fun start() {
    val app = Javalin
      .create { config ->
        //        config.server { ServerFactory.secureServer(appConfig.port, appConfig.securePort) }
        config.requestLogger { ctx, executionTimeMs ->
          log.debug("[${ctx.req.requestURI} completed in ${executionTimeMs}ms]")
        }
      }
      .start(appConfig.port.port)

    JavalinJackson.configure(JsonMapper.mapper())
    app.routes {
      get("") { it.result("Hello, world!") }
      post("") { it.result("Hello, world!") }
      get("login") { ctx ->
        ctx.redirect("https://accounts.google.com/o/oauth2/auth?access_type=offline&prompt=consent&response_type=code&client_id=788673326191-cttogru95u9738b2ml1oovdb6sthvjft.apps.googleusercontent.com&redirect_uri=http%3A%2F%2Fselfi.today%2Foauth%2Fgoogle%2Fcallback&scope=profile&state=secret894887")
      }
      post("oauth/google/callback") { ctx ->
        println(ctx)
        ctx.res.status = 200
      }
      get("oauth/google/callback") { ctx ->
        println(ctx)
        ctx.res.status = 200
      }
    }
  }

  companion object {
    val log = LoggerFactory.getLogger(AuthApp::class.java)
  }
}

val configModule = module {
  single {
    Environment.valueOf(System.getProperty("SELFI_TODAY_ITEM_ENV", DEV.name))
  }
  single { DbConfigs.config(get()) }
  single { AppConfigs.config(get()) }
}

val repoModule = module {
  single { DataSourceWrapper(get()) as DataSource }
  single { AccountJooqRepo(get()) as ItemRepo }
}

val apiModule = module {
  single { AuthRoutes(get()) }
}

fun main(vararg args: String) {
  startKoin {
    printLogger(DEBUG)

    modules(listOf(configModule, repoModule, apiModule))
  }

  AuthApp().start()
}
