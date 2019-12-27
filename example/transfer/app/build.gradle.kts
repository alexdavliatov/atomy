plugins {
  id("org.flywaydb.flyway") version "6.0.2"
}

flyway {
  url = "jdbc:postgresql://dockerhost:9990/transfer"
  user = "transfer_admin"
  password = "yIe7fq5klj78dKOCthDo5r@Jt"
//  locations = arrayOf("classpath:ru/adavliatov/contest/migrations/sql")
}

val flywayVersion: String by project
dependencies {
  implementation(group = "org.flywaydb", name = "flyway-core", version = flywayVersion)
  implementation(group = "org.postgresql", name = "postgresql", version = "9.4.1212")
}
