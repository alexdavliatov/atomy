val javalinVersion: String by project

dependencies {
  api(project(":common:ui:api:core"))
  api(project(":common:ui:api:plugin:javalin"))
  api(project(":example:selfi:item:service"))
  api(project(":example:selfi:item:view"))
  api(project(":example:selfi:shared:type:ref"))

  implementation(project(":common:type:ref:json-impl"))
}
