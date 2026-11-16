-- Creates a trigger function that updates the `updated_at` timestamp on row updates

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Example trigger usage (uncomment and adapt for your table):
-- CREATE TRIGGER trg_update_timestamp
-- BEFORE UPDATE ON your_table
-- FOR EACH ROW
-- EXECUTE FUNCTION update_updated_at_column();
