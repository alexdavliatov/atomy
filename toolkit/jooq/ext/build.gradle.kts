plugins {
  id("nu.studer.jooq")
}

dependencies {
  implementation(project(":common:ext"))
  implementation(project(":common:domain"))
}
