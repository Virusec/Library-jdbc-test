-- liquibase formatted sql

--changeset ashikin:create-books
CREATE TABLE books
(
  id              SERIAL PRIMARY KEY,
  title           VARCHAR(100) NOT NULL,
  author_id       INTEGER NOT NULL REFERENCES authors(id),
  published_year  INTEGER,
  total_copies    INTEGER NOT NULL DEFAULT 1
);
--rollback DROP TABLE books;