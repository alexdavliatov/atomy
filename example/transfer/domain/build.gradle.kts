val moneyVersion: String by project

dependencies {
  implementation(project(":example:transfer:type:money"))

  implementation(project(":common:ext"))
  implementation(project(":common:domain"))
}
