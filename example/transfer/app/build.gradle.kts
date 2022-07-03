plugins {
  id("org.flywaydb.flyway") version "6.0.2"
}

val dbUrl = System.getProperty("DB_URL")
  ?: System.getenv("DB_URL")
  ?: "jdbc:postgresql://dockerhost:9990/transfer_dev"
val dbUser = System.getProperty("DB_USER")
  ?: System.getenv("DB_USER")
  ?: "transfer_admin"
val dbPassword = System.getProperty("DB_PASSWORD")
  ?: System.getenv("DB_PASSWORD")
  ?: "yIe7fq4h#k!KOCthDo5r@Jt"

flyway {
  url = dbUrl
  user = dbUser
  password = dbPassword
  schemas = arrayOf("transaction", "account")
  baselineOnMigrate = true
}

val flywayVersion: String by project
dependencies {
  implementation(group = "org.flywaydb", name = "flyway-core", version = flywayVersion)
  implementation(group = "org.postgresql", name = "postgresql", version = "42.3.3")
}
