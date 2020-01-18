dependencies {
  api(project(":common:domain"))

  implementation(project(":common:type:ref:json-impl"))
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")
}
