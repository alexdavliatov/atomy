val javalinVersion: String by project

dependencies {
  api(project(":common:ui:api:core"))
  api(group = "io.javalin", name = "javalin", version = javalinVersion)

}