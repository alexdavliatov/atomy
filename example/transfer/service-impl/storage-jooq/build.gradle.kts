import nu.studer.gradle.jooq.JooqEdition

plugins {
    java
    kotlin("jvm")
    id("nu.studer.jooq")
}

val dbUrl = System.getProperty("dbUrl") ?: "jdbc:postgresql://dockerhost:5432/transfer"
val dbUser = System.getProperty("dbUser") ?: "transfer_admin"
val dbPassword = System.getProperty("dbPassword") ?: "yIe7fq5klj78dKOCthDo5r@Jt"

jooq {
    version = "3.12.3"
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
                        converter = "ru.adavliatov.atomy.toolkit.jooq.serializeTimestampToInstantConverter"
                        types = """.*TIMESTAMP\ WITHOUT\ TIME\ ZONE.*"""
                    }
                    forcedType {
                        userType = "java.time.Instant"
                        binding = "ru.adavliatov.atomy.toolkit.jooq.serializeTimestampWithTimezoneBinder"
                        types = """.*TIMESTAMP\ WITH\ TIME\ ZONE.*"""
                    }
//                    forcedType {
//                        userType = "com.fasterxml.jackson.databind.JsonNode"
//                        binding = "ru.yandex.contest.toolkit.jooq.converter.JsonBinder"
//                        types = """.*JSONB.*"""
//                    }
                    forcedType {
                        userType = "java.time.Instant"
                        converter = "ru.adavliatov.atomy.toolkit.jooq.serializeTimestampToInstantConverter"
                        types = """.*TIMESTAMP.*"""
                    }
                }
            }
            generate {
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
    implementation(project(":example:transfer:service"))
    implementation(project(":toolkit:jooq:service"))

    implementation(group = "org.postgresql", name = "postgresql", version = "42.2.5")
    "jooqRuntime"("org.postgresql:postgresql:42.2.5")
}

tasks {
    val cleanGenerated by creating(Delete::class) {
        delete = setOf("src/generated") // add accepts argument with Any type
    }
}
