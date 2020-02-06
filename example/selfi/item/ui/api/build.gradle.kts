val javalinVersion: String by project

dependencies {
  api(project(":common:ui:api:core"))
  api(project(":example:selfi:item:service"))
  api(project(":example:selfi:shared:type:ref"))
  api(group = "io.javalin", name = "javalin", version = javalinVersion)

  implementation(project(":common:type:ref:json-impl"))
}
