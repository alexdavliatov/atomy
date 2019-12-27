val jacksonVersion: String by project

dependencies {
  implementation(project(":common:domain"))
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = jacksonVersion)
  implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = jacksonVersion)
  implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310", version = jacksonVersion)
  implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jdk8", version = "2.8.4")
}
