rootProject.name = "ru.adavliatov.atomy"

include(
    ":common",
    ":common:ext",
    ":common:type:name",
    ":common:type:json",
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

    ":example",

    //> selfie
    ":example:selfie",

    ":example:selfie:shared",
    ":example:selfie:shared:type",
    ":example:selfie:shared:type:repeat",
    ":example:selfie:shared:type:duration",

    ":example:selfie:item",
    ":example:selfie:item:type",
    ":example:selfie:item:domain",
    ":example:selfie:item:service",
    ":example:selfie:item:service-impl",
    ":example:selfie:item:ui",
    ":example:selfie:item:ui:api",
    ":example:selfie:item:ui:cmd",
    //< selfie

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
