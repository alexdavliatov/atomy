val javalinVersion: String by project

dependencies {
  api(project(":common:ui:api"))
  api(project(":example:selfi:item:service"))
  api(group = "io.javalin", name = "javalin", version = javalinVersion)

  implementation(project(":common:type:ref:json-impl"))
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")
}
