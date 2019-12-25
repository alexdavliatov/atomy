plugins {
}

val jooqVersion: String by project
dependencies {
  api(group = "org.jooq", name = "jooq", version = jooqVersion)
  api(group = "org.jooq", name = "jooq-meta", version = jooqVersion)
  api(group = "org.jooq", name = "jooq-codegen", version = jooqVersion)
}
