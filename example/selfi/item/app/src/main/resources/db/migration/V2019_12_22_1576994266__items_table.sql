CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS item;

-- accounts
CREATE TABLE IF NOT EXISTS item.items
(
    id          BIGSERIAL,
    uid         UUID         NOT NULL       DEFAULT gen_random_uuid(),
    consumer    JSONB,
    ref         JSONB,
    state       VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),
    modified_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),

    name        VARCHAR(255) NOT NULL,
    duration    JSONB                       DEFAULT '{}',
    owner_id    BIGINT       NOT NULL,
    details     JSONB                       DEFAULT '{}',

    CONSTRAINT item_pk PRIMARY KEY (id),
    CONSTRAINT item_name_unq UNIQUE (owner_id, name)
);

CREATE INDEX IF NOT EXISTS item_uid_idx ON item.items (uid);
CREATE INDEX IF NOT EXISTS item_name_idx ON item.items (name);
CREATE INDEX IF NOT EXISTS item_owner_idx ON item.items (owner_id);
