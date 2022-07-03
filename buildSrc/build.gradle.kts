plugins {
    `kotlin-dsl`
}

repositories {
    maven {
        setUrl("https://plugins.gradle.org/m2/")
    }
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq:3.14.8")
    implementation("org.jooq:jooq-meta:3.14.8")
}
