-- Creates refresh_token table and attaches update trigger to maintain updated_at

CREATE TABLE IF NOT EXISTS refresh_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(512) NOT NULL,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMPTZ,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_refresh_token_token ON refresh_token(token);
CREATE INDEX IF NOT EXISTS idx_refresh_token_user_id ON refresh_token(user_id);

-- Optional: add a foreign key to your users table (uncomment and adapt the table name if needed)
-- ALTER TABLE refresh_token
--     ADD CONSTRAINT fk_refresh_token_user
--     FOREIGN KEY (user_id) REFERENCES users(id);

-- Attach trigger to update "updated_at" on row update.
-- Requires the function update_updated_at_column() to exist (see 001-create-update-trigger-function.sql)
CREATE TRIGGER trg_refresh_token_update
BEFORE UPDATE ON refresh_token
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
