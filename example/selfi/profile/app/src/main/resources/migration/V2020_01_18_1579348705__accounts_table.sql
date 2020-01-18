CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS account;

CREATE TABLE IF NOT EXISTS account.accounts
(
    id          BIGSERIAL,
    uid         UUID         NOT NULL       DEFAULT gen_random_uuid(),
    consumer    JSONB,
    ref         JSONB,
    state       VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),
    modified_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),

    owner_id    BIGINT       NOT NULL,
    origin      VARCHAR(255) NOT NULL,
    details     JSONB                       DEFAULT '{}',

    CONSTRAINT account_pk PRIMARY KEY (id),
    CONSTRAINT account_owner_origin_unq UNIQUE (owner_id, origin)
);

CREATE INDEX IF NOT EXISTS account_uid_idx ON account.accounts (uid);
