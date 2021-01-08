dependencies {
  api(project(":toolkit:jooq:serialize"))
  api(project(":common:type:json:gson-impl"))

  implementation(group = "com.google.code.gson", name = "gson", version = "2.8.6")
}

