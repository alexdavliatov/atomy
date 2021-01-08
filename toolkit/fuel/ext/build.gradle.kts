val fuelVersion: String by project

dependencies {
  api(project(":common:type:json:core"))
  //todo (adavliatov): do we really need it?
  api(project(":common:ui:api:core"))

  api("com.github.kittinunf.fuel:fuel:$fuelVersion")
  api("com.github.kittinunf.fuel:fuel-coroutines:$fuelVersion")
}
