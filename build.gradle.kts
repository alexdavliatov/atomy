import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  base
  `java-platform`
  kotlin("jvm") version "1.3.70" apply false
  id("org.nosphere.gradle.github.actions") version "1.1.0"
  id("io.gitlab.arturbosch.detekt") version "1.7.4"
  id("org.jetbrains.dokka") version "0.10.1" apply false
  id("org.sonarqube") version "2.8"
}

allprojects {
  apply(plugin = "base")

  repositories {
    google()
    jcenter()
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "kotlin")
  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses", "-Xopt-in=kotlin.RequiresOptIn")
  }

//  @Suppress("UnstableApiUsage")
//  tasks.withType<Jar> {
//    archiveBaseName.set("${this@subprojects.parent?.name}-${this@subprojects.name}")
//  }
//
//  @Suppress("UnstableApiUsage")
//  tasks.register<Jar>("sourcesJar") {
//    from(sourceSets.main.get().allSource)
//    archiveClassifier.set("sources")
//  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  dependencies {
    constraints {
      //test: common
      api("org.hamcrest:hamcrest-all:1.3")
      //test: JUnit 5
      api("org.junit.jupiter:junit-jupiter-api:5.6.2")
      runtime("org.junit.jupiter:junit-jupiter-engine:5.6.2")
      //test: Kotest
      runtime("io.kotest:kotest-core-jvm:4.0.1")
      api("io.kotest:kotest-runner-junit5-jvm:4.0.1")
      api("io.kotest:kotest-assertions-core-jvm:4.0.1")
    }
  }
}

javaPlatform {
  allowDependencies()
}

tasks {
//  val copyToLib by creating(Copy::class) {
//    into("$buildDir/libs")
//    from(configurations.compile)
//  }
//
//  val stage by creating {
//    dependsOn(
//      copyToLib
//    )
//  }
}