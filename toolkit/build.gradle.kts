plugins {
  kotlin("jvm")
}


allprojects {
  group = "ru.adavliatov.atomy.toolkit"

    dependencies {
        api(kotlin("stdlib"))
        api(kotlin("reflect"))
    }
}
