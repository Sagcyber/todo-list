-- liquibase formatted sql

-- changeset fabi:002-add-task-type

ALTER TABLE tasks
    ADD COLUMN type VARCHAR(50) NOT NULL DEFAULT 'OTHER';

-- rollback ALTER TABLE tasks DROP COLUMN type;