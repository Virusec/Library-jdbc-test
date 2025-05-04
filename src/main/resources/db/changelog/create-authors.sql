-- liquibase formatted sql

--changeset ashikin:create-authors
CREATE TABLE authors
(
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL
);
--rollback DROP TABLE authors;