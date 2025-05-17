package com.example.dao;

import com.example.DbManager;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Borrowing;
import com.example.model.Reader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс LibraryDao предоставляет методы для управления библиотекой:
 * добавления и получения книг, авторов, читателей, а также работы с записями о выдаче книг.
 * @author Anatoliy Shikin
 */
public class LibraryDao {
    /**
     * Добавляет новую книгу в базу данных.
     *
     * @param book объект книги
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void addBook(Book book) throws SQLException {
        String sql = """
                INSERT INTO books(title,author_id,published_year,total_copies)
                VALUES (?,?,?,?)
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthorId());
            preparedStatement.setInt(3, book.getPublishedYear());
            preparedStatement.setInt(4, book.getTotalCopies());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Добавляет нового автора в базу данных.
     *
     * @param author объект автора
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void addAuthor(Author author) throws SQLException {
        String sql = """
                INSERT INTO authors(first_name,last_name)
                VALUES (?,?)
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Добавляет нового читателя в базу данных.
     *
     * @param reader объект читателя
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void addReader(Reader reader) throws SQLException {
        String sql = """
                INSERT INTO readers(first_name,last_name,email)
                VALUES (?,?,?)
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, reader.getFirstName());
            preparedStatement.setString(2, reader.getLastName());
            preparedStatement.setString(3, reader.getEmail());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Возвращает список всех книг, включая информацию об авторе
     * и количестве книг "на руках".
     *
     * @return список книг
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public List<Book> getAllBooks() throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = """
                SELECT
                b.id,
                a.first_name,a.last_name,
                b.title,b.published_year,b.total_copies,
                COUNT(br.id) AS borrowed_count
                FROM books b
                LEFT JOIN borrowings br ON br.book_id=b.id AND br.returned_date IS NULL
                LEFT JOIN authors a ON b.author_id=a.id
                GROUP BY b.id,b.title,b.published_year,b.total_copies,a.first_name,a.last_name
                ORDER BY b.id
                """;
        Connection connection = DbManager.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Author author = Author.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .build();
                Book book = Book.builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .publishedYear(resultSet.getInt("published_year"))
                        .totalCopies(resultSet.getInt("total_copies"))
                        .borrowedCount(resultSet.getInt("borrowed_count"))
                        .author(author)
                        .build();
                list.add(book);
            }
        }
        return list;
    }

    /**
     * Возвращает список всех зарегистрированных читателей.
     *
     * @return список читателей
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public List<Reader> getAllReaders() throws SQLException {
        List<Reader> readerList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM readers
                """;
        Connection connection = DbManager.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                readerList.add(new Reader(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
        }
        return readerList;
    }

    /**
     * Возвращает список всех авторов.
     *
     * @return список авторов
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authorList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM authors
                """;
        Connection connection = DbManager.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                authorList.add(new Author(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
        }
        return authorList;
    }

    /**
     * Обновляет данные книги по её идентификатору.
     *
     * @param bookId идентификатор книги
     * @param book   обновлённые данные книги
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void updateBookById(int bookId, Book book) throws SQLException {
        String sql = """
                UPDATE books
                SET title=?,author_id=?,published_year=?,total_copies=?
                WHERE id=?
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthorId());
            preparedStatement.setInt(3, book.getPublishedYear());
            preparedStatement.setInt(4, book.getTotalCopies());
            preparedStatement.setInt(5, bookId);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Удаляет книгу из базы данных по её идентификатору.
     *
     * @param bookId идентификатор книги
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void deleteBookById(int bookId) throws SQLException {
        String sql = """
                DELETE FROM books
                WHERE id=?
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        }
    }
//TODO тест
    /**
     * Вызывает хранимую процедуру для добавления автора и книги одновременно.
     *
     * @param authorFirstName имя автора
     * @param authorLastName  фамилия автора
     * @param bookTitle       название книги
     * @param publishedYear   год публикации
     * @param totalCopies     общее количество экземпляров
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void addAuthorAndBookTogether(
            String authorFirstName,
            String authorLastName,
            String bookTitle,
            int publishedYear,
            int totalCopies
    ) throws SQLException {
        String sql = """
                CALL add_author_and_book_proc(?,?,?,?,?)
                """;
        Connection connection = DbManager.getConnection();
        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, authorFirstName);
            callableStatement.setString(2, authorLastName);
            callableStatement.setString(3, bookTitle);
            callableStatement.setInt(4, publishedYear);
            callableStatement.setInt(5, totalCopies);
            callableStatement.execute();
        }
    }

    /**
     * Выполняет поиск книг по ключевому слову в названии или имени автора.
     *
     * @param keyWord ключевое слово для поиска
     * @return список найденных книг
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public List<Book> searchBooks(String keyWord) throws SQLException {
        String sql = """
                SELECT
                b.id,
                a.first_name,a.last_name,
                b.title,b.published_year,b.total_copies,
                COUNT(br.id) AS borrowed_count
                FROM books b
                LEFT JOIN borrowings br ON br.book_id=b.id AND br.returned_date IS NULL
                JOIN authors a ON b.author_id=a.id
                WHERE LOWER(b.title) LIKE ? OR LOWER(a.first_name || ' ' || a.last_name) LIKE ?
                GROUP BY b.id,b.title,b.published_year,b.total_copies,a.first_name,a.last_name
                ORDER BY b.id
                """;
        List<Book> bookList = new ArrayList<>();
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String src = "%" + keyWord.toLowerCase() + "%";
            preparedStatement.setString(1, src);
            preparedStatement.setString(2, src);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Author author = Author.builder()
                            .id(resultSet.getInt("id"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .build();
                    Book book = Book.builder()
                            .id(resultSet.getInt("id"))
                            .title(resultSet.getString("title"))
                            .publishedYear(resultSet.getInt("published_year"))
                            .totalCopies(resultSet.getInt("total_copies"))
                            .borrowedCount(resultSet.getInt("borrowed_count"))
                            .author(author)
                            .build();
                    bookList.add(book);
                }
            }
        }
        return bookList;
    }

    /**
     * Добавляет запись о выдаче книги читателю.
     *
     * @param readerId идентификатор читателя
     * @param bookId   идентификатор книги
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void borrowBook(int readerId, int bookId) throws SQLException {
        String sql = """
                INSERT INTO borrowings(reader_id, book_id)
                VALUES (?, ?)
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, readerId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Обновляет запись о выдаче книги, устанавливая дату возврата на текущую.
     *
     * @param borrowingId идентификатор записи о заимствовании
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public void returnBook(int borrowingId) throws SQLException {
        String sql = """
                UPDATE borrowings
                SET returned_date = CURRENT_DATE
                WHERE id = ?
                """;
        Connection connection = DbManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrowingId);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Возвращает список всех выданных книг.
     *
     * @return список заимствований
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    public List<Borrowing> getAllBorrowings() throws SQLException {
        var list = new ArrayList<Borrowing>();
        String sql = """
                SELECT * FROM borrowings
                """;
        Connection connection = DbManager.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(new Borrowing(
                        resultSet.getInt("id"),
                        resultSet.getInt("reader_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("borrowed_date").toLocalDate(),
                        resultSet.getDate("returned_date") != null
                                ? resultSet.getDate("returned_date").toLocalDate() : null
                ));
            }
        }
        return list;
    }
}