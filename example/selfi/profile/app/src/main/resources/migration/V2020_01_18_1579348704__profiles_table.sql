CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS profile;

CREATE TABLE IF NOT EXISTS profile.profiles
(
    id          BIGSERIAL,
    uid         UUID         NOT NULL       DEFAULT gen_random_uuid(),
    consumer    JSONB,
    ref         JSONB,
    state       VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),
    modified_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),

    name        VARCHAR(255) NOT NULL,

    CONSTRAINT profile_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS profile_uid_idx ON profile.profiles (uid);
