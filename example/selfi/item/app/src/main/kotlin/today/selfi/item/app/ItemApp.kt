package today.selfi.item.app

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.plugin.json.JavalinJackson
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.core.logger.Level.DEBUG
import org.koin.dsl.module
import org.slf4j.LoggerFactory
import ru.adavliatov.atomy.common.type.json.impl.JacksonContext
import ru.adavliatov.atomy.common.ui.api.serializer.JsonMapper
import today.selfi.item.app.config.AppConfig
import today.selfi.item.app.config.AppConfigs
import today.selfi.item.app.config.DataSourceWrapper
import today.selfi.item.app.config.DbConfigs
import today.selfi.item.app.config.Environment
import today.selfi.item.app.config.Environment.DEV
import today.selfi.item.service.repo.ItemJooqRepo
import today.selfi.item.service.repo.ItemRepo
import today.selfi.item.ui.api.ItemRoutes
import today.selfi.item.ui.api.serialize.ItemModule.itemModule
import javax.sql.DataSource

class ItemApp : KoinComponent {
  private val appConfig by inject<AppConfig>()
  private val itemRoutes by inject<ItemRoutes>()
  private val jsonContext by inject<JacksonContext>()

  fun start() {
    val app = Javalin
      .create { config ->
        config.requestLogger { ctx, executionTimeMs ->
          log.debug("[${ctx.req.requestURI} completed in ${executionTimeMs}ms]")
        }
      }
      .start(appConfig.port.port)

    JavalinJackson.configure(jsonContext.mapper)

    itemRoutes.routes("item")(app)
    app.routes {
      get("") { it.result("Hello, world!") }

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
  single { JacksonContext(JsonMapper.mapper(itemModule)) }
  single { DbConfigs.config(get()) }
  single { AppConfigs.config(get()) }
}

val repoModule = module {
  single { DataSourceWrapper(get()) as DataSource }
  single { ItemJooqRepo(get(), get()) as ItemRepo }
}

val apiModule = module {
  single { ItemRoutes(get()) }
}

fun main(vararg args: String) {
  startKoin {
    printLogger(DEBUG)

    modules(
      listOf(
        configModule,
        repoModule,
        apiModule
      )
    )
  }

  ItemApp().start()
}
