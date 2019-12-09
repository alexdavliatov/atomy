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
    ":example:transfer:service",
    ":example:transfer:ui:api",
    ":example:transfer:ui:cmd",
    ":example:transfer:app"
)
