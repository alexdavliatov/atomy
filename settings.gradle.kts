rootProject.name = "ru.adavliatov.atomy"

include(
  ":common",
  ":common:ext",

  ":common:type:name",
  ":common:type:chunk",
  ":common:type:page",
  ":common:type:error",
  ":common:type:event",

  ":common:type:json:core",
  ":common:type:json:jackson-impl",
  ":common:type:json:gson-impl",

  ":common:type:ref:core",
  ":common:type:ref:json-impl",
  ":common:type:ref:text-impl",

  ":common:domain",
  ":common:view:core",
  ":common:view:plugin-jackson",

  ":common:service",

  ":common:ui:api",
  ":common:ui:api:core",
  ":common:ui:api:plugin:javalin",
  ":common:ui:cli",
  ":common:ui:bot:core",
  ":common:ui:bot:telegram",
  ":common:ui:bot:slack",
  ":common:ui:bot:q",

  ":common:app:conf",

  ":toolkit",

  ":toolkit:jooq",
  ":toolkit:jooq:ext",
  ":toolkit:jooq:serialize",
  ":toolkit:jooq:service",
  ":toolkit:jooq:plugin-jackson",
  ":toolkit:jooq:plugin-gson",

  ":toolkit:fuel",
  ":toolkit:fuel:ext",
  ":toolkit:fuel:service",

  ":example",

  //> transfer
  ":example:transfer",
  ":example:transfer:type",
  ":example:transfer:type:money",
  ":example:transfer:domain",
  //> services
  ":example:transfer:service",
  ":example:transfer:service-impl",
  ":example:transfer:service-impl:storage-jooq",
  //> ui
  ":example:transfer:ui:api",
  ":example:transfer:app"
  //< transfer
)
