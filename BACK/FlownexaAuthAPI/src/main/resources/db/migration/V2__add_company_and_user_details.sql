ALTER TABLE organizations
    ADD COLUMN legal_name VARCHAR(200) NOT NULL DEFAULT '',
    ADD COLUMN tax_id VARCHAR(50) NOT NULL DEFAULT '',
    ADD COLUMN industry VARCHAR(100),
    ADD COLUMN country VARCHAR(100) NOT NULL DEFAULT '',
    ADD COLUMN phone VARCHAR(30),
    ADD COLUMN contact_email VARCHAR(255);

ALTER TABLE organizations
    ADD CONSTRAINT uq_organizations_tax_id UNIQUE (tax_id);

ALTER TABLE users
    ADD COLUMN full_name VARCHAR(150) NOT NULL DEFAULT '',
    ADD COLUMN phone VARCHAR(30),
    ADD COLUMN job_title VARCHAR(100);
