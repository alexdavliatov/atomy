plugins {
  id("org.flywaydb.flyway") version "6.0.2"
}

flyway {
  url = "jdbc:postgresql://dockerhost:5432/selfi_dev"
  user = "selfi_admin"
  password = "yIe7fq4h#k!KOCthDo5r@Jt"
//  locations = arrayOf("classpath:ru/yandex/contest/migrations/sql")
}

val flywayVersion: String by project
dependencies {
  implementation(group = "org.flywaydb", name = "flyway-core", version = flywayVersion)
  implementation(group = "org.postgresql", name = "postgresql", version = "9.4.1212")
}