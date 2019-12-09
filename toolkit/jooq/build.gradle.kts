plugins {
  kotlin("jvm")
  id("nu.studer.jooq") apply false
}


allprojects {
  group = "ru.adavliatov.atomy.toolkit.jooq"
}

subprojects {
  val jooqVersion: String by project

  dependencies {
    implementation(group = "org.jooq", name = "jooq", version = jooqVersion)
    implementation(group = "org.jooq", name = "jooq-meta", version = jooqVersion)
    implementation(group = "org.jooq", name = "jooq-codegen", version = jooqVersion)
  }
}
