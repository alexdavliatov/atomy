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

    ":example",
    ":example:transfer",
    ":example:transfer:ext",
    ":example:transfer:domain",
    ":example:transfer:service",
    ":example:transfer:ui:api",
    ":example:transfer:ui:cmd",
    ":example:transfer:app"
)
