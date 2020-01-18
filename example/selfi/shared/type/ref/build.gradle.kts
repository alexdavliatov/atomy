dependencies {
  api(project(":common:type:json:jackson-impl"))
  api(project(":common:type:ref:json-impl"))

  api(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")
}