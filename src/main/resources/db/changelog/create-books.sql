-- liquibase formatted sql

--changeset ashikin:create-books
CREATE TABLE books
(
  id              SERIAL PRIMARY KEY,
  title           VARCHAR(100) NOT NULL,
  author_id       INTEGER NOT NULL REFERENCES author(id),
  published_year  INTEGER
);
--rollback DROP TABLE books;