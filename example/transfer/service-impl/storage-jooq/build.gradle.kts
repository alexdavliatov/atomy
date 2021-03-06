import nu.studer.gradle.jooq.JooqEdition

plugins {
  java
  kotlin("jvm")
  id("nu.studer.jooq")
}

val dbUrl = System.getProperty("DB_URL")
  ?: System.getenv("DB_URL")
  ?: "jdbc:postgresql://dockerhost:9990/transfer_dev"
val dbUser = System.getProperty("DB_USER")
  ?: System.getenv("DB_USER")
  ?: "transfer_admin"
val dbPassword = System.getProperty("DB_PASSWORD")
  ?: System.getenv("DB_PASSWORD")
  ?: "yIe7fq4h#k!KOCthDo5r@Jt"

jooq {
  version = "3.13.1"
  edition = JooqEdition.OSS

  val java = project.the<JavaPluginConvention>()

  "transfer"(java.sourceSets["main"]!!) {
    jdbc {
      driver = "org.postgresql.Driver"
      url = dbUrl
      user = dbUser
      password = dbPassword
    }
    generator {
      database {
        name = "org.jooq.meta.postgres.PostgresDatabase"
        includes = ".*"
        excludes = "flyway_schema_version"
        schemata {
          schema {
            withInputSchema("account")
          }
          schema {
            withInputSchema("transaction")
          }
        }
        isIncludeTables = true
        isIncludeRoutines = true
        isIncludeTriggerRoutines = true
        isIncludePackages = true
        isIncludePackageRoutines = true
        isIncludePackageUDTs = true
        isIncludePackageConstants = true
        isIncludeUDTs = true
        isIncludeSequences = true
        isIncludeIndexes = true
        isIncludePrimaryKeys = true
        isIncludeUniqueKeys = true
        isIncludeForeignKeys = true
        forcedTypes {
          forcedType {
            userType = "java.time.Instant"
            converter = "ru.adavliatov.atomy.toolkit.jooq.serialize.TimestampToInstantConverter"
            types = """.*TIMESTAMP\ WITHOUT\ TIME\ ZONE.*"""
          }
          forcedType {
            userType = "java.time.Instant"
            binding = "ru.adavliatov.atomy.toolkit.jooq.serialize.TimestampWithTimezoneBinder"
            types = """.*TIMESTAMP\ WITH\ TIME\ ZONE.*"""
          }
          forcedType {
            userType = "ru.adavliatov.atomy.common.type.json.impl.JacksonJson"
            binding = "ru.adavliatov.atomy.toolkit.jooq.serialize.plugin.JacksonJsonBinder"
            types = ".*JSON.*"
          }
          forcedType {
            userType = "java.time.Instant"
            converter = "ru.adavliatov.atomy.toolkit.jooq.serialize.TimestampToInstantConverter"
            types = """.*TIMESTAMP.*"""
          }
        }
      }
      generate {
        isGeneratedAnnotation = false
        isDeprecated = false
        isRecords = true
        isImmutablePojos = true
        isFluentSetters = true

        isDaos = true
      }
      target {
        packageName = "ru.adavliatov.atomy.example.transfer.service.repo.generated"
        directory = "src/generated/kotlin"
      }
      strategy {
        name = "org.jooq.codegen.DefaultGeneratorStrategy"
      }
    }
  }
}


dependencies {
  implementation(project(":common:type:ref:json-impl"))
  implementation(project(":example:transfer:service"))
  implementation(project(":toolkit:jooq:service"))
  implementation(project(":toolkit:jooq:plugin-jackson"))

  implementation(group = "org.postgresql", name = "postgresql", version = "42.2.5")
  "jooqRuntime"("org.postgresql:postgresql:42.2.5")
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.1")
}

tasks {
  @Suppress("UNUSED_VARIABLE")
  val cleanGenerated by creating(Delete::class) {
    delete = setOf("src/generated") // add accepts argument with Any type
  }
}
