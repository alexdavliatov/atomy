val jacksonVersion: String by project

dependencies {
  api(project(":common:type:ref:json-impl"))
  api(project(":common:type:json:jackson-impl"))
  api(project(":common:type:chunk"))
  api(project(":common:type:page"))
  api(project(":common:domain"))
  api(project(":common:view:core"))
  api(project(":common:service"))
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = jacksonVersion)
  implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = jacksonVersion)
  implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310", version = jacksonVersion)
  implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jdk8", version = "2.8.4")
}
