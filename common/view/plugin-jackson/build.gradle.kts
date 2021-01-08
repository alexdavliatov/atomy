val jacksonVersion: String by project

dependencies {
  api(project(":common:domain"))
  api(project(":common:view:core"))
  api(project(":common:type:json:jackson-impl"))
  api(project(":common:type:ref:json-impl"))

  api("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
  api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
  api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
  api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
}
