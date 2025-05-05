package com.example.dao;

import com.example.DbManager;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Anatoliy Shikin
 */
public class LibraryDao {
    public void addBook(Book book) throws SQLException {
        String sql = """
                INSERT INTO books(title,author_id,published_year,total_copies)
                VALUES (?,?,?,?)
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthorId());
            if (book.getPublishedYear() != null) {
                preparedStatement.setInt(3, book.getPublishedYear());
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            preparedStatement.setInt(4, book.getTotalCopies());
            preparedStatement.executeUpdate();
        }
    }

    public void addAuthor(Author author) throws SQLException {
        String sql = """
                INSERT INTO authors(first_name,last_name)
                VALUES (?,?)""";
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.executeUpdate();
        }
    }

    public void addReader(Reader reader) throws SQLException {
        String sql = """
                INSERT INTO readers(first_name,last_name,email)
                VALUES (?,?,?)""";
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, reader.getFirstName());
            preparedStatement.setString(2, reader.getLastName());
            preparedStatement.setString(3, reader.getEmail());
            preparedStatement.executeUpdate();
        }
    }
}
