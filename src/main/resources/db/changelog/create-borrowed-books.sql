-- liquibase formatted sql

--changeset ashikin:create-borrowed-books
CREATE TABLE borrowed_books
(
  id SERIAL  PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL
);
--rollback DROP TABLE borrowed_books;