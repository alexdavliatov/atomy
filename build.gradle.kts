import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  base
  `java-library`
  kotlin("jvm") version "1.3.61" apply false
}

allprojects {
  ext {
    set("meta", false)
  }

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
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
  }

  @Suppress("UnstableApiUsage")
  tasks.withType<Jar> {
    archiveBaseName.set("${this@subprojects.parent?.name}-${this@subprojects.name}")
  }

  @Suppress("UnstableApiUsage")
  tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
  }

}
