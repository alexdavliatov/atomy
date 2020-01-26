plugins {
  kotlin("jvm")
}

allprojects {
  group = "today.selfi.item.ui"

  tasks.withType<PublishToMavenRepository> {
    enabled = false
  }
}
