import groovy.lang.GroovyObject
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  base
  `java-library`
  kotlin("jvm") version "1.3.50" apply false
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
  }

  tasks.withType<Jar> {
    archiveBaseName.set("${this@subprojects.parent?.name}-${this@subprojects.name}")
  }

  tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
  }

}
