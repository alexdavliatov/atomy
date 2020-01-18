import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.flywaydb.flyway") version "6.0.2"
  id("com.github.johnrengelman.shadow") version "5.2.0"
}

flyway {
  url = "jdbc:postgresql://dockerhost:9991/profile_dev"
  user = "profile_admin"
  password = "yIe!df#Do5r@fGU"
//  locations = arrayOf("classpath:ru/adavliatov/contest/migrations/sql")
}

val koinVersion: String by project
val logbackVersion: String by project

val shadowJar: com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar by tasks
shadowJar.apply {
  manifest.attributes.apply {
    put("Implementation-Title", "Profiles service")
    put("Implementation-Version", "1.0.0")
    put("Main-Class", "today.selfi.app.ProfileAppKt")

    @Suppress("DEPRECATION")
    archiveName = "profile-app.jar"
  }
}

val flywayVersion: String by project
dependencies {
  implementation(project(":common:ui:api"))
//  implementation(project(":example:selfi:profile:ui:api"))
  implementation(project(":example:selfi:profile:service-impl:storage-jooq"))
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")

  implementation(group = "org.flywaydb", name = "flyway-core", version = flywayVersion)
  implementation(group = "org.postgresql", name = "postgresql", version = "9.4.1212")

  implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = "1.3.61")

  implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")
  implementation(group = "com.zaxxer", name = "HikariCP", version = "3.1.0")
  implementation(group = "org.koin", name = "koin-core", version = koinVersion)
  implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.29")

  implementation(group = "ch.qos.logback", name = "logback-core", version = logbackVersion)
  implementation(group = "ch.qos.logback", name = "logback-classic", version = logbackVersion)

  testImplementation(group = "org.koin", name = "koin-test", version = koinVersion)

}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}

tasks {
  val shadowJar by getting(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class)

  val buildProfile by creating(Exec::class) {
    dependsOn(shadowJar)
    commandLine = listOf("docker-compose", "build", "profile")
  }

  val buildProfileFromSources by creating { dependsOn(jar, buildProfile) }

  val profileUp by creating(Exec::class) {
    mustRunAfter(buildProfileFromSources)
    commandLine = listOf("docker-compose", "up", "-d", "profile")
  }
  @Suppress("UNUSED_VARIABLE") val profileUpFromSources by creating { dependsOn(buildProfileFromSources, profileUp) }

  val image = "registry.net/adavliatov/selfi-profile"
  val tagProfile by creating(Exec::class) {
    dependsOn(buildProfileFromSources)
    commandLine = listOf("docker", "tag", "$image:latest", "$image:${System.getProperty("version")}")
  }

  @Suppress("UNUSED_VARIABLE") val pushProfile by creating(Exec::class) {
    dependsOn(tagProfile)
    commandLine = listOf("docker", "push", "$image:${System.getProperty("version")}")
  }
}
