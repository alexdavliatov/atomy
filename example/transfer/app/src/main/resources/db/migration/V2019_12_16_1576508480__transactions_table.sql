CREATE SCHEMA IF NOT EXISTS transaction;

-- transactions
CREATE TABLE IF NOT EXISTS transaction.transactions
(
    id           BIGSERIAL,
    uid          UUID         NOT NULL       DEFAULT gen_random_uuid(),
    ref          JSONB,
    state        VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),
    modified_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc'),

    from_account BIGINT NOT NULL,
    to_account   BIGINT NOT NULL,
    operation    VARCHAR(255) NOT NULL,
    money        JSONB,

    CONSTRAINT transactions_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS accounts_uid_idx ON transaction.transactions (uid);
