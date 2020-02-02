dependencies {
  api(project(":common:domain"))
  api(project(":example:selfi:shared:type:repeat"))
  api(project(":example:selfi:shared:type:duration"))
  api(project(":example:selfi:shared:type:ref"))

  implementation(project(":common:type:ref:json-impl"))
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")
}
