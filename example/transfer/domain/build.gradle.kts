val moneyVersion: String by project

dependencies {
  implementation(project(":example:transfer:ext"))

  implementation(project(":common:ext"))
  implementation(project(":common:domain"))
}
