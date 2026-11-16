
CREATE TABLE departments (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL REFERENCES organizations(id),
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_departments_org_name UNIQUE (organization_id, name)
);

ALTER TABLE users
    ADD COLUMN department_id UUID REFERENCES departments(id);