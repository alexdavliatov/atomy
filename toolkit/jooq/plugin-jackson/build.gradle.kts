dependencies {
  api(project(":toolkit:jooq:serialize"))
  api(project(":common:type:json:jackson-impl"))

  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")
}
