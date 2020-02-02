rootProject.name = "ru.adavliatov.atomy"

include(
  ":common",
  ":common:ext",
  ":common:type:name",

  ":common:type:error",

  ":common:type:json:core",
  ":common:type:json:jackson-impl",
  ":common:type:json:gson-impl",

  ":common:type:ref:core",
  ":common:type:ref:json-impl",
  ":common:type:ref:text-impl",

  ":common:domain",
  ":common:service",
  ":common:ui:api",
  ":common:ui:cmd",
  ":common:app",

  ":toolkit",
  ":toolkit:jooq",
  ":toolkit:jooq:ext",
  ":toolkit:jooq:serialize",
  ":toolkit:jooq:service",
  ":toolkit:jooq:plugin-jackson",
  ":toolkit:jooq:plugin-gson",

  ":example",

  //> selfi
  ":example:selfi",

  ":example:selfi:shared",
  ":example:selfi:shared:type",
  ":example:selfi:shared:type:ref",
  ":example:selfi:shared:type:repeat",
  ":example:selfi:shared:type:duration",

  ":example:selfi:item",
  ":example:selfi:item:type",
  ":example:selfi:item:domain",
  ":example:selfi:item:service",
  ":example:selfi:item:service-impl",
  ":example:selfi:item:service-impl:storage-jooq",
  ":example:selfi:item:ui",
  ":example:selfi:item:ui:api",
  ":example:selfi:item:ui:cmd",
  ":example:selfi:item:app",

  ":example:selfi:profile",
  ":example:selfi:profile:domain",
  ":example:selfi:profile:service",
  ":example:selfi:profile:service-impl",
  ":example:selfi:profile:service-impl:storage-jooq",
  ":example:selfi:profile:app",

  ":example:selfi:auth",
  ":example:selfi:auth:domain",
  ":example:selfi:auth:service",
  ":example:selfi:auth:service-impl",
  ":example:selfi:auth:service-impl:storage-jooq",
  ":example:selfi:auth:ui:api",
  ":example:selfi:auth:app",
  //< selfi

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
  ":example:transfer:ui:cmd",
  ":example:transfer:app"
  //< transfer
)
