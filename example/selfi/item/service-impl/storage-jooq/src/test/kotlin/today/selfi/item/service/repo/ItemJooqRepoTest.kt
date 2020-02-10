package today.selfi.item.service.repo

import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import ru.adavliatov.atomy.common.type.json.impl.JacksonContext
import today.selfi.item.domain.ItemDsl.item
import javax.sql.DataSource

class ItemJooqRepoTest {
  lateinit var ds: DataSource
  val repo: ItemRepo = ItemJooqRepo(ds, JacksonContext(ObjectMapper()))
}

fun main() {
  val config = HikariConfig()
    .apply {
      jdbcUrl = "jdbc:postgresql://dockerhost:5432/selfi_dev"
      username = "selfi_admin"
      password = "yIe7fq4h#k!KOCthDo5r@Jt"
    }
  HikariDataSource(config).use { ds ->
    val repo: ItemRepo = ItemJooqRepo(ds, JacksonContext(ObjectMapper()))

    val item = item {}
    repo.create(item)

    val item1 = repo.findAll().first()
    println(item1)
  }
}