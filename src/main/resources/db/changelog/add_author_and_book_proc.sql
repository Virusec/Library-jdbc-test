--liquibase formatted sql

--changeset ashikin:add_author_and_book_proc splitStatements:false endDelimiter:$$;
CREATE OR REPLACE PROCEDURE add_author_and_book_proc(
    in_p_first_name   VARCHAR,
    in_p_last_name    VARCHAR,
    in_p_title        VARCHAR,
    in_p_published    INTEGER,
    in_p_total_copies INTEGER
)
LANGUAGE plpgsql
AS $$
DECLARE
  new_author_id INTEGER;
BEGIN
  INSERT INTO authors(first_name, last_name)
  VALUES (in_p_first_name, in_p_last_name)
  RETURNING id INTO new_author_id;
  INSERT INTO books(title, author_id, published_year, total_copies)
  VALUES (in_p_title, new_author_id, in_p_published, in_p_total_copies);
END;
$$;
--rollback DROP PROCEDURE add_author_and_book_proc;