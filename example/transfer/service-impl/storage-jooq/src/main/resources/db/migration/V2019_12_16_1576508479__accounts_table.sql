CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS account;

-- accounts
CREATE TABLE IF NOT EXISTS account.accounts
(
    id          BIGSERIAL,
    uid         UUID         NOT NULL       DEFAULT gen_random_uuid(),
    ref         JSONB,
    state       VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),
    modified_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),

    name        VARCHAR(255) NOT NULL,
    wallet      JSONB,

    CONSTRAINT accounts_pk PRIMARY KEY (id),
    CONSTRAINT accounts_name_unq UNIQUE (name)
);

CREATE INDEX IF NOT EXISTS accounts_uid_idx ON account.accounts (uid);
CREATE INDEX IF NOT EXISTS accounts_name_idx ON account.accounts (name);
