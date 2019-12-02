plugins {
  kotlin("jvm")
}


allprojects {
  //To not clashe module names: https://github.com/gradle/gradle/issues/847
  group = "ru.adavliatov.atomy.common"

  dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
  }
}
