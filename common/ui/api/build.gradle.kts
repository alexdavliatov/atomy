allprojects {
  //To not clash module names: https://github.com/gradle/gradle/issues/847
  group = "ru.adavliatov.atomy.ui.api"

  dependencies {
    api(kotlin("stdlib"))
    api(kotlin("reflect"))
  }
}
