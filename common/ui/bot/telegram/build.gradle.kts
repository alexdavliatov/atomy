val logbackVersion: String by project

dependencies {
  api("org.telegram:telegrambots:4.6") {
    exclude(group = "org.telegram", module = "telegrambots-abilities")
  }
//  api("org.telegram:telegrambots-abilities:4.6")

  implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.29")
  implementation(group = "ch.qos.logback", name = "logback-core", version = logbackVersion)
  implementation(group = "ch.qos.logback", name = "logback-classic", version = logbackVersion)
}