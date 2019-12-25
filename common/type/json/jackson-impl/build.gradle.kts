dependencies {
  api(project(":common:type:json:core"))
  api(project(":common:ext"))

  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")
}