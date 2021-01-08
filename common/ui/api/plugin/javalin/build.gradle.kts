val javalinVersion: String by project

dependencies {
  api(project(":common:view"))
  api(project(":common:ui:api:core"))
  api(group = "io.javalin", name = "javalin", version = javalinVersion)

  api("io.swagger.core.v3:swagger-core:2.0.9")
}