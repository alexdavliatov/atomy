rootProject.name = "ru.adavliatov.atomy"

include(
    ":common",
    ":common:ext",
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
)
