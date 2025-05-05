-- liquibase formatted sql

--changeset ashikin:create-borrowings
CREATE TABLE borrowings
(
  id            SERIAL PRIMARY KEY,
  reader_id     INTEGER NOT NULL REFERENCES readers(id),
  book_id       INTEGER NOT NULL REFERENCES books(id),
  borrowed_date DATE DEFAULT CURRENT_DATE,
  returned_date DATE
);
--rollback DROP TABLE borrowings;