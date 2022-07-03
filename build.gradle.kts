import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  base
  `java-platform`
  kotlin("jvm") version "1.7.0" apply false
  id("org.nosphere.gradle.github.actions") version "1.1.0"
  id("io.gitlab.arturbosch.detekt") version "1.21.0-RC2"
  id("org.jetbrains.dokka") version "1.7.0" apply false
  id("org.sonarqube") version "2.8"
  id("com.revolut.jooq-docker") version "0.3.7" apply false
}

allprojects {
  apply(plugin = "base")

  repositories {
    mavenCentral()
    maven {
      setUrl("https://plugins.gradle.org/m2/")
    }
  }
}

subprojects {
  apply(plugin = "kotlin")
  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
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