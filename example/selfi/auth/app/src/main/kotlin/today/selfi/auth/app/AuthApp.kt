package today.selfi.auth.app

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.crud
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
import today.selfi.item.ui.api.ItemRoutes
import today.selfie.item.service.repo.ItemJooqRepo
import today.selfie.item.service.repo.ItemRepo
import javax.sql.DataSource

class ItemApp : KoinComponent {
  private val appConfig by inject<AppConfig>()
  private val itemRoutes by inject<ItemRoutes>()

  fun start() {
    val app = Javalin
      .create { config ->
        config.requestLogger { ctx, executionTimeMs ->
          log.debug("[${ctx.req.requestURI} completed in ${executionTimeMs}ms]")
        }
      }
      .start(appConfig.port.port)

    JavalinJackson.configure(JsonMapper.mapper())
    app.routes {
      crud("item/:id", itemRoutes)
    }
  }

  companion object {
    val log = LoggerFactory.getLogger(ItemApp::class.java)
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
  single { ItemJooqRepo(get()) as ItemRepo }
}

val apiModule = module {
  single { ItemRoutes(get()) }
}

fun main(vararg args: String) {
  startKoin {
    printLogger(DEBUG)

    modules(listOf(configModule, repoModule, apiModule))
  }

  ItemApp().start()
}
