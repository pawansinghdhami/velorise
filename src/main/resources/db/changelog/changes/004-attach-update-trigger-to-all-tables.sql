-- Adds `updated_at` column if missing and attaches a single shared trigger function
-- that updates `updated_at` on row UPDATE to every table in the public schema.
-- Requires: update_updated_at_column() function to already exist (see 001-create-update-trigger-function.sql)
-- Skips common migration/internal tables; adjust skip_tables array as needed.

DO $$
DECLARE
  r RECORD;
  trgname text;
  skip_tables text[] := ARRAY['databasechangelog','databasechangeloglock','flyway_schema_history','spatial_ref_sys'];
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_proc WHERE proname = 'update_updated_at_column') THEN
    RAISE NOTICE 'Trigger function update_updated_at_column() not found. Create it before running this script.';
    RETURN;
  END IF;

  FOR r IN
    SELECT table_schema, table_name
    FROM information_schema.tables
    WHERE table_type = 'BASE TABLE'
      AND table_schema = 'public'
      AND NOT (table_name = ANY (skip_tables))
  LOOP
    trgname := 'trg_' || r.table_name || '_update';

    -- ensure updated_at column exists
    EXECUTE format('ALTER TABLE %I.%I ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()', r.table_schema, r.table_name);

    -- create trigger only if it doesn't already exist on that table
    IF NOT EXISTS (
      SELECT 1 FROM pg_trigger t
      JOIN pg_class c ON t.tgrelid = c.oid
      JOIN pg_namespace n ON c.relnamespace = n.oid
      WHERE n.nspname = r.table_schema
        AND c.relname = r.table_name
        AND t.tgname = trgname
    ) THEN
      EXECUTE format('CREATE TRIGGER %I BEFORE UPDATE ON %I.%I FOR EACH ROW EXECUTE FUNCTION update_updated_at_column()', trgname, r.table_schema, r.table_name);
      RAISE NOTICE 'Created trigger % for table %', trgname, r.table_name;
    END IF;
  END LOOP;
END;
$$ LANGUAGE plpgsql;
