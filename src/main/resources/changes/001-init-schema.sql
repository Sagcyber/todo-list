-- liquibase formatted sql

-- changeset fabi:001-init-schema

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       created_at TIMESTAMP NOT NULL
);

CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(200) NOT NULL,
                       description VARCHAR(1000),
                       completed BOOLEAN NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       due_date TIMESTAMP,
                       user_id BIGINT NOT NULL,
                       category_id BIGINT,

                       CONSTRAINT fk_task_user
                           FOREIGN KEY (user_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE,

                       CONSTRAINT fk_task_category
                           FOREIGN KEY (category_id)
                               REFERENCES categories(id)
);

-- rollback DROP TABLE tasks;
-- rollback DROP TABLE categories;
-- rollback DROP TABLE users;