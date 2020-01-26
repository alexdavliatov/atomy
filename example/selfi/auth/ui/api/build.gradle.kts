val javalinVersion: String by project

dependencies {
  api(project(":common:ui:api"))
  api(project(":example:selfi:item:service"))
  api(project(":example:selfi:shared:type:ref"))
  api(group = "io.javalin", name = "javalin", version = javalinVersion)
  implementation(project(":common:type:ref:json-impl"))

  api(group = "org.pac4j", name = "javalin-pac4j", version = "2.0.0")
  api(group = "org.pac4j", name = "pac4j", version = "4.0.0-RC2", ext = "pom")
  api(group = "org.pac4j", name = "pac4j-core", version = "4.0.0-RC2")
  implementation(group = "org.pac4j", name = "pac4j-http", version = "4.0.0-RC2")
  implementation(group = "org.pac4j", name = "pac4j-oauth", version = "4.0.0-RC2")
  implementation(group = "org.pac4j", name = "pac4j-oidc", version = "4.0.0-RC2")
  implementation(group = "org.pac4j", name = "pac4j-jwt", version = "4.0.0-RC2")
  implementation(group = "org.pac4j", name = "pac4j-config", version = "4.0.0-RC2")
}
